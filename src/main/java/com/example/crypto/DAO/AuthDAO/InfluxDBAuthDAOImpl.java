package com.example.crypto.DAO.AuthDAO;

import com.example.crypto.model.CoinModel;
import com.example.crypto.responseObjects.CoinWithCurrentPrice;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.log4testng.Logger;

import java.time.Instant;
import java.util.List;
@Component
public class InfluxDBAuthDAOImpl implements InfluxDBAuthDAO {
    Logger log = Logger.getLogger(InfluxDBAuthDAOImpl.class);
    @Autowired
    InfluxDBClient influxDBClient;
    @Override
    public void updateDbWithLatestCoinPrice(CoinWithCurrentPrice result) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        Instant updated=Instant.now();
        CoinModel coinModel = new CoinModel(result.getCoin().getId(),result.getPrice(), updated);
        writeApi.writeMeasurement(WritePrecision.MS,coinModel);
    }

    @Override
    public void updateDbWithLatestBatchCoinPrice(List<CoinModel> result) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        log.info("updating db");
        writeApi.writeMeasurements(WritePrecision.MS,result);
        log.info("db update success!!");
    }

    @Override
    public void updateBatches(List<Point> points) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writePoints(points);
    }

    @Override
    public boolean checkDbConnection() {
        return influxDBClient.ping();
    }
}
