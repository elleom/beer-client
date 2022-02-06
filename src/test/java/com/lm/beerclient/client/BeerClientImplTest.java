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

        BeerPagedList beerPagedList = beerPagedListMono.block(); //blocks into a single thread > stays on the thread
        assertThat(beerPagedList).isNotNull();
        assertThat(beerPagedList.getContent().size()).isGreaterThan(0);
    }

    @Test
    void listBeersPageSize10() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10,null, null, null);

        BeerPagedList beerPagedList = beerPagedListMono.block(); //blocks into a single thread > stays on the thread
        assertThat(beerPagedList).isNotNull();
        assertThat(beerPagedList.getContent().size()).isEqualTo(10);
    }

    @Test
    void listBeersEmpty() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(10, 20,null, null, null);

        BeerPagedList beerPagedList = beerPagedListMono.block(); //blocks into a single thread > stays on the thread
        assertThat(beerPagedList).isNotNull();
        assertThat(beerPagedList.getContent().size()).isEqualTo(0);
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