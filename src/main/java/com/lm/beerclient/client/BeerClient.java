package com.lm.beerclient.client;

import com.lm.beerclient.model.Beer;
import com.lm.beerclient.model.BeerPagedList;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author el_le
 * @since 09/12/2021 16:04
 */
public interface BeerClient {

    Mono<Beer> getBeerById(UUID id, Boolean showInventoryOnHand);

    Mono<Beer> getBeerByName(String name);

    Mono<Beer> getBeerByUPC(String upc);

    Mono<BeerPagedList> listBeers(Integer pageNumber,
                                  Integer pagedSize,
                                  String beerName,
                                  String beerStyle,
                                  Boolean showInventoryOnHand);

    Mono<ResponseEntity> createBeer(Beer beer);

    Mono<ResponseEntity> updateBeer(Beer beer);

    Mono<ResponseEntity> deleteBeerById(UUID id);
}
