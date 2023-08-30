package com.example.crypto.responseObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class CoinWithCurrentPrice {
    public CoinWithCurrentPrice(Coin coin){
        this.coin=coin;
    }

    public CoinWithCurrentPrice(Coin coin,BigDecimal price){
        this.coin=coin;
        this.price=price;
    }

    private Coin coin;
    private BigDecimal price;
}
