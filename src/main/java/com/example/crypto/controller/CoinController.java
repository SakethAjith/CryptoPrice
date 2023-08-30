package com.example.crypto.controller;

import com.example.crypto.model.CoinModel;
import com.example.crypto.request.MarketData;
import com.example.crypto.responseObjects.Coin;
import com.example.crypto.responseObjects.CoinWithCurrentPrice;
import com.example.crypto.service.CoinService;
import com.example.crypto.service.InfluxDBService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.crypto.endpoints.CoinGecko.*;

@Component
public class CoinController {
    private static final String CURRENCY="usd";
    private static final int API_RATE_INTERVAL=2*60*1000;
    Logger log = Logger.getLogger(CoinController.class);
    @Autowired
    WebClient webClient;

    @Autowired
    Gson gson;

    @Autowired
    CoinService coinService;

    @Autowired
    InfluxDBService influxDBService;



    public CoinWithCurrentPrice getCoin(String id) throws IOException {
        //TODO:Simplify
        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(SIMPLE+"price")
                .query("ids="+id).query("vs_currencies="+CURRENCY)
                .build();
        String url=uriComponents.toUriString();

        //TODO:Seperate util to make requests
        String response= webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class).onErrorContinue((e,i)->{
                    log.error("error");
                })
                .block();

        return coinService.getCoin(id,response);
    }

    public List<Coin> getCoinsList() throws IOException{
        //TODO:Simplify
        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(COINS+"list")
                .build();
        String url=uriComponents.toUriString();

        //TODO:Seperate util to make requests
        List<Coin> results= Arrays.stream(Objects.requireNonNull(webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Coin[].class)
                .block())).toList();
        return coinService.getCoinsList(results);
    }

    public List<MarketData> getCoinMarket(int coinsPerPage,int pageNumber) throws IOException, InterruptedException {
        //TODO:Simplify
        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(MARKET)
                .query("vs_currency=usd").query("order=market_cap_desc")
                .query("per_page="+coinsPerPage).query("page="+pageNumber)
                .query("sparkline=false").query("locale=en")
                .build();
        String url=uriComponents.toUriString();

        //TODO:Seperate util to make requests
        List<MarketData> response= List.of(Objects.requireNonNull(webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(MarketData[].class).onErrorContinue((e, i) -> {
                    log.error("error");
                })
                .block()));



        log.info(response.size());
        for (MarketData md : response) {
            log.info(md.getName());
            log.info(md.getCurrent_price());
        }


        return response;
    }

    public void updateMarketCoinPrices() throws InterruptedException {
        int coinsPerpage = 250;
        int pageNumber = 1;
        List<MarketData> mds = new ArrayList<>();
        for (int i = pageNumber; i <= 40; i++) {
            try {
                mds.addAll(getCoinMarket(coinsPerpage, i));

                if(i%3==0) {
                    Thread.sleep(API_RATE_INTERVAL);
                    List<CoinModel> coins = new ArrayList<>();
                    for (MarketData md : mds) {
                        if(md!=null && md.getCurrent_price()!=null) {
                            coins.add(new CoinModel(md.getId(), new BigDecimal(md.getCurrent_price()), Instant.now()));
                        }
                    }
                    influxDBService.updateDbWithLatestBatchCoinPrice(coins);//750 coin batch
                }
            }catch (IOException e){
                log.error("Exception!: ",e);
                log.info("retrying request");
                Thread.sleep(API_RATE_INTERVAL);
                i--;
            }
        }
    }


    private CoinModel convertCoinWithCurrentPriceToCoinModel(CoinWithCurrentPrice coin){
        CoinModel coinModel=new CoinModel(coin.getCoin().getId(),coin.getPrice(), Instant.now());
        return coinModel;
    }


}
