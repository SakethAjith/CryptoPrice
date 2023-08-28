package com.example.crypto.DAO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public interface InfluxDBDAO {
    Map<String, Map<Long, BigDecimal>> retrieveFromDb();
    Map<Instant, BigDecimal> retrieveCoinPriceHistoryFromDb(String name, Integer historyFrom);
    BigDecimal retrieveLatestCoinPriceFromDb(String name);
}
