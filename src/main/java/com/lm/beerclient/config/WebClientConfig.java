package com.lm.beerclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author el_le
 * @since 09/12/2021 16:26
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(WebClientProperties.BASE_URL).build();
    }

}
