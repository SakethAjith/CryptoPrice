package com.example.crypto.DAO;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.log4testng.Logger;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class InfluxDBDAOImpl implements InfluxDBDAO{
    private static final String PRICE="price";
    private static final String MODEL="CoinModel";
    private static final String COIN_NAME="name";
    private static final String TIME="_time";
    Logger log = Logger.getLogger(InfluxDBDAOImpl.class);
    @Autowired
    InfluxDBClient influxDBClient;

    @Override
    public Map<String, Map<Long, BigDecimal>> retrieveFromDb() {
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

    @Override
    public Map<Instant, BigDecimal> retrieveCoinPriceHistoryFromDb(String name, Integer historyFrom) {
        ZonedDateTime input=ZonedDateTime.now();
        Instant start= input.minusDays(historyFrom).toInstant();

        log.info("start value = "+start+"last updated value ="+start);
        String flux="from(bucket: \"CryptoBucket\") |> range(start: "+start+")"+
                "|> filter(fn: (r)=>r._measurement ==\"CoinModel\")\n" +
                "|> filter(fn: (r)=>r._field == \"price\")"+
                "|> filter(fn: (r)=>r[\"name\"] == \""+name+"\")";

        QueryApi queryApi=influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux);
        Map<Instant,BigDecimal> prices=new HashMap<>();
        for(FluxTable fluxTable:tables){
            List<FluxRecord> records=fluxTable.getRecords();
            for(FluxRecord record:records){
//                log.info(record.getValues());
                log.info(record.getTime());
                log.info(record.getValue());
                prices.put(record.getTime(),new BigDecimal((Double) record.getValue()));
//                price=new BigDecimal((Double) record.getValue());
            }
        }
        return prices;
    }

    @Override
    public BigDecimal retrieveLatestCoinPriceFromDb(String name) {
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
}
