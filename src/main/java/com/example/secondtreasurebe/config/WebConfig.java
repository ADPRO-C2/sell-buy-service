package com.example.secondtreasurebe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // CORS mapping for payment methods
        registry.addMapping("/api/**")
                .allowedOriginPatterns("http://localhost:3001")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH")
                .allowedHeaders("Origin", "Content-Type", "Accept")
                .allowCredentials(true);

        registry.addMapping("/api/**")
                .allowedOriginPatterns("https://frontend-adpro-c2.vercel.app")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH")
                .allowedHeaders("Origin", "Content-Type", "Accept")
                .allowCredentials(true);

        // CORS mapping for topups
        registry.addMapping("/order/**")
                .allowedOriginPatterns("http://localhost:3001")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH")
                .allowedHeaders("Origin", "Content-Type", "Accept")
                .allowCredentials(true);

        registry.addMapping("/order/**")
                .allowedOriginPatterns("https://frontend-adpro-c2.vercel.app/")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH")
                .allowedHeaders("Origin", "Content-Type", "Accept")
                .allowCredentials(true);

        registry.addMapping("/cartlisting/**")
                .allowedOriginPatterns("http://localhost:3001")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH")
                .allowedHeaders("Origin", "Content-Type", "Accept")
                .allowCredentials(true);

        registry.addMapping("/cartlisting/**")
                .allowedOriginPatterns("https://frontend-adpro-c2.vercel.app/")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH")
                .allowedHeaders("Origin", "Content-Type", "Accept")
                .allowCredentials(true);
    }

}