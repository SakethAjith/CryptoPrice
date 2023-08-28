package com.example.crypto.service;

import com.example.crypto.DAO.AuthDAO.InfluxDBAuthDAO;
import com.example.crypto.DAO.InfluxDBDAO;
import com.example.crypto.model.CoinModel;
import com.example.crypto.responseObjects.CoinWithCurrentPrice;
import com.influxdb.client.write.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class InfluxDBService {

    @Autowired
    InfluxDBDAO influxDBDAO;

    @Autowired
    InfluxDBAuthDAO influxDBAuthDAO;

    public void updateDbWithLatestCoinPrice(CoinWithCurrentPrice coin){
        influxDBAuthDAO.updateDbWithLatestCoinPrice(coin);
    }


    public void updateDbWithLatestBatchCoinPrice(List<CoinModel> coins){
        influxDBAuthDAO.updateDbWithLatestBatchCoinPrice(coins);
    }

    public void updateBatches(List<Point> points){
        influxDBAuthDAO.updateBatches(points);
    }

    public Map<String, Map<Long, BigDecimal>> retrieveFromDb(){
        return influxDBDAO.retrieveFromDb();
    }

    public BigDecimal retrieveLatestCoinPriceFromDb(String name){
        return influxDBDAO.retrieveLatestCoinPriceFromDb(name);
    }

    public Map<Instant,BigDecimal> retrieveCoinPriceHistoryFromDb(String name, Integer historyFrom){
        return influxDBDAO.retrieveCoinPriceHistoryFromDb(name,historyFrom);
    }


    public boolean checkDbConnection(){
        return influxDBAuthDAO.checkDbConnection();
    }
}
