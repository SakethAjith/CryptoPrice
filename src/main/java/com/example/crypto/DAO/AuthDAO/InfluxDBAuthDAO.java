package com.example.crypto.DAO.AuthDAO;

import com.example.crypto.model.CoinModel;
import com.example.crypto.responseObjects.CoinWithCurrentPrice;
import com.influxdb.client.write.Point;

import java.util.List;

public interface InfluxDBAuthDAO {
    void updateDbWithLatestCoinPrice(CoinWithCurrentPrice result);

    void updateDbWithLatestBatchCoinPrice(List<CoinModel> result);

    void updateBatches(List<Point> points);

    boolean checkDbConnection();
}
