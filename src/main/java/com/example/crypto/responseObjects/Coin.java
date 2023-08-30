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

    private String id;

    private String symbol;

    private String name;

}
