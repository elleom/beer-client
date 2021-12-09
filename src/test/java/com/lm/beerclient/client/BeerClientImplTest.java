package com.lm.beerclient.client;

import com.lm.beerclient.config.WebClientConfig;
import com.lm.beerclient.model.BeerPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author el_le
 * @since 09/12/2021 19:06
 */
class BeerClientImplTest {

    BeerClientImpl beerClient;

    @BeforeEach
    void setUp() {
        beerClient = new BeerClientImpl(new WebClientConfig().webClient());
    }

    @Test
    void getBeerById() {
    }

    @Test
    void getBeerByName() {
    }

    @Test
    void getBeerByUPC() {
    }

    @Test
    void listBeers() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null,null, null, null);

        BeerPagedList beerPagedList = beerPagedListMono.block(); //blocks into a single thread
        assertThat(beerPagedList).isNotNull();
    }

    @Test
    void createBeer() {
    }

    @Test
    void updateBeer() {
    }

    @Test
    void deleteBeerById() {
    }
}