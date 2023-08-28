package com.example.crypto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarketData {
    String id;
    String name;
    String symbol;
    String current_price;
}
