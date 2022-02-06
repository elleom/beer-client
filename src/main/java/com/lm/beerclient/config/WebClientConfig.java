package com.lm.beerclient.config;

import io.netty.handler.logging.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

/**
 * @author el_le
 * @since 09/12/2021 16:26
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(WebClientProperties.BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .wiretap("reactor.netty.client.HttpClient", LogLevel.DEBUG,
                                AdvancedByteBufFormat.TEXTUAL))) //enables logging
                .build();
    }

}
