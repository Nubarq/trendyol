package com.ecommerce.trendyoldemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Bütün endpointlər üçün
                        .allowedOrigins("*") // İstənilən mənşə üçün
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // İcazə verilən metodlar
            }
        };
    }
}
