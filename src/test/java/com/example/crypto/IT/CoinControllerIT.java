package com.example.crypto.IT;

import com.example.crypto.controller.CoinController;
import com.example.crypto.request.MarketData;
import com.example.crypto.responseObjects.Coin;
import com.example.crypto.responseObjects.CoinWithCurrentPrice;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class CoinControllerIT {

    @Autowired
    CoinController controller;

    @Test
    void getCoinTest() throws IOException {
        CoinWithCurrentPrice coin=controller.getCoin("bitcoin");
        Assert.assertNotNull(coin.getPrice());
        Assert.assertNotNull(coin.getCoin());
    }

    @Test
    void getCoinsListTest() throws IOException, InterruptedException {
        List<Coin> coins=controller.getCoinsList();
        Assert.assertTrue(coins.size()>0);
    }

    @Test
    void getCoinMarketTest() throws IOException, InterruptedException {
        List<MarketData> marketData=controller.getCoinMarket(250,1);
        Assert.assertTrue(marketData.size()>0);
    }


    @Test
    void updateMarketCoinPricesTest() throws InterruptedException {
        controller.updateMarketCoinPrices();
    }


}
