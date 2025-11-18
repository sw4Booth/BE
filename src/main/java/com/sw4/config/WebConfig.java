package com.sw4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "https://booth.suhan.io",
                        "https://sw4booth.s3.ap-northeast-2.amazonaws.com",
                        "http://booth.suhan.io",
                        "https://api-booth.suhan.io"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD")
                .allowedHeaders("*")
                .exposedHeaders("ETag", "Location")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
