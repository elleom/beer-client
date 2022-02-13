package com.lm.beerclient.client;

import com.lm.beerclient.config.WebClientConfig;
import com.lm.beerclient.model.Beer;
import com.lm.beerclient.model.BeerPagedList;
import com.lm.beerclient.model.v2.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

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

        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null,null, null, null);
        BeerPagedList beerPagedList = beerPagedListMono.block(); //blocks into a single thread > stays on the thread
        UUID beerId = beerPagedList.getContent().get(0).getId();
        Mono<Beer> beerMono = beerClient.getBeerById(beerId, false);
        Beer beer = beerMono.block();
        assertThat(beer).isNotNull();


    }

    @Test
    void getBeerByName() {
    }

    @Test
    void getBeerByUPC() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null,null, null, null);
        BeerPagedList beerPagedList = beerPagedListMono.block(); //blocks into a single thread > stays on the thread
        String beerUpc = beerPagedList.getContent().get(0).getUpc();
        Mono<Beer> beerMono = beerClient.getBeerByUPC(beerUpc);
        Beer beer = beerMono.block();
        assertThat(beer).isNotNull();
        assertThat(beer.getUpc()).isEqualTo(beerUpc);
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
        Beer beer = Beer.builder()
                .beerName("Dogfishhead Ale")
                .beerStyle(BeerStyle.ALE)
                .upc("12313213")
                .price(new BigDecimal("10.99"))
                .build();

        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.createBeer(beer);
        ResponseEntity responseEntity = responseEntityMono.block();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    void updateBeer() {
    }

    @Test
    void deleteBeerById() {
    }
}