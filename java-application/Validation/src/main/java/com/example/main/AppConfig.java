package com.example.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public InMemoryLoginStore loginStore() {
        return new InMemoryLoginStore();
    }
}
