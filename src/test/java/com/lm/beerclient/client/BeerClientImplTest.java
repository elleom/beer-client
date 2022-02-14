package com.lm.beerclient.client;

import com.lm.beerclient.config.WebClientConfig;
import com.lm.beerclient.model.Beer;
import com.lm.beerclient.model.BeerPagedList;
import com.lm.beerclient.model.v2.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void functionalGetBeerById() throws InterruptedException {
        AtomicReference<String> beerName = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        beerClient.listBeers(null, null,null, null, null)
                .map(beerPagedList -> beerPagedList.getContent().get(0).getId())
                .map(beerId -> beerClient.getBeerById(beerId, false))
                .flatMap(beerMono -> beerMono)
                .subscribe(beer -> {
                    System.out.println(beer.getBeerName());
                    beerName.set(beer.getBeerName());
                    assertThat(beer.getBeerName()).isEqualTo("Mango Bobs");
                    countDownLatch.countDown(); //
                });

        countDownLatch.await();
        assertThat(beerName.get()).isEqualTo("Mango Bobs");

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
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null,null, null, null);
        BeerPagedList beerPagedList = beerPagedListMono.block(); //blocks into a single thread > stays on the thread

        Beer beerOld = beerPagedList.getContent().get(0);
        Beer updatedBeer = Beer.builder().beerName("New Name")
                .beerStyle(beerOld.getBeerStyle())
                .price(beerOld.getPrice())
                .upc(beerOld.getUpc())
                .build();

        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.updateBeer(beerOld.getId(), updatedBeer);
        ResponseEntity<Void> responseEntity = responseEntityMono.block();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    void deleteBeerById() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null,null, null, null);
        BeerPagedList beerPagedList = beerPagedListMono.block(); //blocks into a single thread > stays on the thread
        Beer beerOld = beerPagedList.getContent().get(0);

        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeerById(beerOld.getId());
        ResponseEntity<Void> responseEntity = responseEntityMono.block();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteBeerByIdNotFound() {
        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeerById(UUID.randomUUID());
        assertThrows(WebClientException.class, () -> {
            ResponseEntity<Void> responseEntity = responseEntityMono.block();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        });
    }

    @Test
    void handleException() {
        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeerById(UUID.randomUUID());
        ResponseEntity<Void> responseEntity = responseEntityMono.onErrorResume(throwable -> {
            if (throwable instanceof WebClientResponseException){
                WebClientResponseException webClientException = (WebClientResponseException) throwable;
                return Mono.just(ResponseEntity.status(webClientException.getStatusCode()).build());
            } else {
                throw new RuntimeException(throwable);
            }
        }).block();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}