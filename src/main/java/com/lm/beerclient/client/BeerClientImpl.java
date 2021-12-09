package com.lm.beerclient.client;

import com.lm.beerclient.model.Beer;
import com.lm.beerclient.model.BeerPagedList;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author el_le
 * @since 09/12/2021 16:13
 */
public class BeerClientImpl implements BeerClient {

    @Override
    public Mono<Beer> getBeerById(UUID id, Boolean showInventoryOnHand) {
        return null;
    }

    @Override
    public Mono<Beer> getBeerByName(String name) {
        return null;
    }

    @Override
    public Mono<Beer> getBeerByUPC(String upc) {
        return null;
    }

    @Override
    public Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pagedSize, String beerName, String beerStyle, Boolean showInventoryOnHand) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> createBeer(Beer beer) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> updateBeer(Beer beer) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> deleteBeerById(UUID id) {
        return null;
    }
}
