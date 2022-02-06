package com.lm.beerclient.config;

import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

/**
 * @author el_le
 * @since 09/12/2021 16:28
 */
public class WebClientProperties {

    public static final String BASE_URL = "http://api.springframework.guru";
    public static final String BEER_V1_PATH = "/api/v1/beer";
    public static final String BEER_V1_PATH_BEER_UPC = "api/v1/beerUpc";
}
