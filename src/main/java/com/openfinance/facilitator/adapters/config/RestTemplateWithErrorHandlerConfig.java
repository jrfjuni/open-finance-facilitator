package com.openfinance.facilitator.adapters.config;

import com.openfinance.facilitator.adapters.inbound.controllers.handler.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateWithErrorHandlerConfig {

    @Bean
    RestTemplate restTemplateWithErrorHandler() {
        return new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }
}
