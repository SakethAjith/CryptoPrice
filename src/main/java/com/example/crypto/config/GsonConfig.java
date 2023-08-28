package com.example.crypto.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {
    @Bean
    public Gson gsonBean(){
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
