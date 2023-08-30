package com.example.crypto.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository
@Getter
@Setter
@NoArgsConstructor
public class CoinRepository {
    private String name;
    private BigDecimal price;

    public CoinRepository(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
