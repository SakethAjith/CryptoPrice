package com.example.crypto.config;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDbConfig {
    @Value("${influx-Url}")
    private String url;
    @Value("${influx-Token}")
    private String token;

    @Value("${influx-Org}")
    private String org;

    @Value("${influx-Bucket}")
    private String bucket;

    @Value("${influx-User}")
    private String user;

    @Value("${influx-Password}")
    private String password;




    @Bean
    public InfluxDBClient influxDBClientConnect(){
        InfluxDBClient influxDBClient= InfluxDBClientFactory.create(url, token.toCharArray(),org,bucket);
        return influxDBClient;
    }
}
