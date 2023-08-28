package com.example.crypto.responseObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Coin {
    public Coin(String id) {
        this.id = id;
    }

    String id;

    String symbol;

    String name;

}
