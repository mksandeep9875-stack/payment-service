package com.menon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean(name = "auth-service-validate")
    public WebClient webClientAuthService(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://localhost:8085/customer/v1/validate")
                .filter(new LoggingWebClientFilter())
                .build();
    }

}
