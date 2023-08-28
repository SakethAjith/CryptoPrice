package com.example.crypto.UT;

import com.example.crypto.service.InfluxDBService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@SpringBootTest
public class InfluxDBServiceTest {

    @Autowired
    InfluxDBService influxDBService;

    @Test
    public void retrieveFromDbTest(){
        influxDBService.retrieveFromDb();
    }

    @Test
    public void retrieveLatestCoinPriceFromDbTest(){
        BigDecimal price=influxDBService.retrieveLatestCoinPriceFromDb("bitcoin");
        Assert.assertNotNull(price);
    }

    @Test
    public void retrieveCoinPriceHistoryFromDbTest(){
        Map<Instant,BigDecimal> priceHistory=influxDBService.retrieveCoinPriceHistoryFromDb("bitcoin",2);
        Assert.assertNotNull(priceHistory);
    }
}
