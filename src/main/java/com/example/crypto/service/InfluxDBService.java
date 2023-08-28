package com.example.crypto.service;

import com.example.crypto.controller.CoinController;
import com.example.crypto.model.CoinModel;
import com.example.crypto.responseObjects.CoinWithCurrentPrice;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.log4testng.Logger;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.crypto.controller.CoinController.CURRENCY;
import static java.lang.Math.ceil;

@Service
public class InfluxDBService {
    public static final String PRICE="price";
    public static final String MODEL="CoinModel";
    public static final String COIN_NAME="name";
    public static final String TIME="_time";
    Logger log = Logger.getLogger(InfluxDBService.class);

    @Autowired
    InfluxDBClient influxDBClient;

    public void updateDbWithLatestCoinPrice(CoinWithCurrentPrice result){
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        Instant updated=Instant.now();
        CoinModel coinModel = new CoinModel(result.getCoin().getId(),result.getPrice(), updated);
        writeApi.writeMeasurement(WritePrecision.MS,coinModel);
    }


    public void updateDbWithLatestBatchCoinPrice(List<CoinModel> result){
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        log.info("updating db");
        writeApi.writeMeasurements(WritePrecision.MS,result);
        log.info("db update success!!");
    }

    public void updateBatches(List<Point> points){
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writePoints(points);
    }

    public Map<String, Map<Long, BigDecimal>> retrieveFromDb(){
        ZonedDateTime input=ZonedDateTime.now();
            Instant start= input.minusDays(2).toInstant();

        log.info("start value = "+start+"last updated value ="+start);
        String flux="from(bucket: \"CryptoBucket\") |> range(start: "+start+")"+
                "|> filter(fn: (r)=>r._measurement ==\"CoinModel\")\n" +
                "|> filter(fn: (r)=>r._field == \"price\")";
        QueryApi queryApi=influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux);

        Map<String,Map<Long,BigDecimal>> resultMap=new HashMap<>();

        for(FluxTable fluxTable:tables){
            List<FluxRecord> records=fluxTable.getRecords();
            for(FluxRecord fluxRecord:records){
                String measurement=fluxRecord.getMeasurement();
                String field=fluxRecord.getField();

                if(measurement.equalsIgnoreCase(MODEL) && field.equalsIgnoreCase(PRICE)){
                    BigDecimal price = new BigDecimal((Double) fluxRecord.getValue());
                    if(price.intValue()>0) {
                        log.info("Found!!");

                        String name = (String) fluxRecord.getValues().get(COIN_NAME);
                        log.info(fluxRecord.getValues().get(COIN_NAME));
                        log.info(price);

                        Instant time = (Instant) fluxRecord.getValues().get(TIME);
                        log.info(time);

                        if (!resultMap.containsKey(name)) {
                            Map<Long, BigDecimal> priceGraph = new HashMap<>();
                            resultMap.put(name, priceGraph);
                        }
                        resultMap.get(name).put(time.toEpochMilli(), price);
                    }
                }
            }
        }
        return resultMap;
    }

    public BigDecimal retrieveLatestCoinPriceFromDb(String name){
        ZonedDateTime input=ZonedDateTime.now();
        Instant start= input.minusDays(2).toInstant();

        log.info("start value = "+start+"last updated value ="+start);
        String flux="from(bucket: \"CryptoBucket\") |> range(start: "+start+")"+
                "|> filter(fn: (r)=>r._measurement ==\"CoinModel\")\n" +
                "|> filter(fn: (r)=>r._field == \"price\")"+
                "|> filter(fn: (r)=>r[\"name\"] == \""+name+"\")"+
                "|>last()";

        QueryApi queryApi=influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux);
        BigDecimal price=null;
        for(FluxTable fluxTable:tables){
            List<FluxRecord> records=fluxTable.getRecords();
            for(FluxRecord record:records){
                log.info(record.getValues());
                log.info(record.getValue());
                price=new BigDecimal((Double) record.getValue());
            }
        }
        return price;
    }


    public boolean checkDbConnection(){
        retrieveFromDb();
        log.info(Instant.now().getNano());
        return influxDBClient.ping();
    }
}
