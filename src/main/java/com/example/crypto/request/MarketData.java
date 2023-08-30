package com.example.crypto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarketData {
    private String id;
    private String name;
    private String symbol;
    private String current_price;
}
