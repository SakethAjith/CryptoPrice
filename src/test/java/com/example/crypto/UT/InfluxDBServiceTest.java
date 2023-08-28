package com.example.crypto.UT;

import com.example.crypto.service.InfluxDBService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InfluxDBServiceTest {

    @Autowired
    InfluxDBService influxDBService;

    @Test
    public void retrieveFromDb(){
        influxDBService.retrieveFromDb();
    }
}
