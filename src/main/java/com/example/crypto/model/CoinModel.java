package com.example.crypto.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Measurement(name = "CoinModel")
public class CoinModel {
    @Column(tag = true)
    String name;

    @Column(tag = false)
    BigDecimal price;

    @Column(timestamp = true)
    Instant LastUpdated;

}
