package com.example.crypto.service;

import com.example.crypto.responseObjects.Coin;
import com.example.crypto.responseObjects.CoinWithCurrentPrice;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.log4testng.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.example.crypto.controller.CoinController.CURRENCY;
import static com.example.crypto.endpoints.FilePaths.COINS_LIST;

@Service
public class CoinService {
    Logger log = Logger.getLogger(CoinService.class);

    @Autowired
    InfluxDBService influxDBService;

    @Autowired
    Gson gson;

    static String getFieldValue(String json, String field) throws JsonParseException, IOException {
        JsonFactory factory = new JsonFactory();
        try (JsonParser parser = factory.createParser(json)) {
            for (JsonToken token; (token = parser.nextToken()) != null; ) {
                if (token == JsonToken.FIELD_NAME && parser.getCurrentName().equals(field)) {
                    token = parser.nextToken();
                    // check token here if object or array should throw exception instead of returning null
                    return parser.getValueAsString();
                }
            }
        }
        return null; // or throw exception if "not found" shouldn't return null
    }


    public CoinWithCurrentPrice getCoin(String id,String response) throws IOException {
        Coin coin=new Coin();
        coin.setId(id);
        CoinWithCurrentPrice result = new CoinWithCurrentPrice(coin);
        String price=getFieldValue(response,CURRENCY);
        if(price!=null){
            result.setPrice(new BigDecimal(price));
            log.info("price retrieved for "+id);
        }
        return result;
    }

    public List<Coin> getCoinsList(List<Coin> coins) throws IOException {
        for(Coin coin:coins){
            log.info(coin.getName());
        }
        gson.toJson(coins,new FileWriter(COINS_LIST));
        return coins;
    }

    public Map<String, Map<Long, BigDecimal>> getCoinBoard(){
        return influxDBService.retrieveFromDb();
    }
}
