package com.training.springtips.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public Blabler blabler(){
        return new Blabler();
    }
}
