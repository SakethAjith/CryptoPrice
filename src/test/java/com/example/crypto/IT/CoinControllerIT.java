package com.example.crypto.IT;

import com.example.crypto.controller.CoinController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CoinControllerIT {

    @Autowired
    CoinController controller;

    @Test
    public void getCoinBoardTest() throws IOException, InterruptedException {
        List<String> coins=new ArrayList<>();
        coins.add("bitcoin");
        coins.add("ethereum");
        controller.getCoinBoard(true,coins);
    }

    @Test
    void getCoinMarketTest() throws IOException, InterruptedException {
        controller.getCoinMarket(250,1);
    }

    @Test
    void getNCoinsCoinBoardTest() throws IOException, InterruptedException {
        controller.getNCoinsCoinBoard(0,100);
    }

    @Test
    void updateMarketCoinPricesTest() throws IOException, InterruptedException {
        controller.updateMarketCoinPrices();
    }


}
