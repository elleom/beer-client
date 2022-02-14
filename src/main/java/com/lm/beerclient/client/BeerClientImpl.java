package com.lm.beerclient.client;

import com.lm.beerclient.config.WebClientProperties;
import com.lm.beerclient.model.Beer;
import com.lm.beerclient.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

/**
 * @author el_le
 * @since 09/12/2021 16:13
 */

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    private final WebClient webClient;

    @Override
    public Mono<Beer> getBeerById(UUID id, Boolean showInventoryOnHand) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
                        .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                        .build(id))
                .retrieve()
                .bodyToMono(Beer.class);
    }

    @Override
    public Mono<Beer> getBeerByName(String name) { return null;
    }

    @Override
    public Mono<Beer> getBeerByUPC(String upc) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(WebClientProperties.BEER_V1_PATH_BEER_UPC).build(upc))
                .retrieve()
                .bodyToMono(Beer.class);
    }

    @Override
    public Mono<BeerPagedList> listBeers(Integer pageNumber,
                                         Integer pageSize,
                                         String beerName,
                                         String beerStyle,
                                         Boolean showInventoryOnHand) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(WebClientProperties.BEER_V1_PATH)
                                .queryParamIfPresent("pageNumber", Optional.ofNullable(pageNumber))
                                .queryParamIfPresent("pageSize", Optional.ofNullable(pageSize))
                                .queryParamIfPresent("beerName", Optional.ofNullable(beerName))
                                .queryParamIfPresent("beerStyle", Optional.ofNullable(beerStyle))
                                .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                                .build()
                )
                .retrieve()
                .bodyToMono(BeerPagedList.class); //retrives and parse
    }

    @Override
    public Mono<ResponseEntity<Void>> createBeer(Beer beer) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH).build())
                .body(BodyInserters.fromValue(beer))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Mono<ResponseEntity<Void>> updateBeer(UUID id, Beer beer) {

        return webClient.put()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
                        .build(id))
                .body(BodyInserters.fromValue(beer))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteBeerById(UUID id) {

        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
                .build(id))
                .retrieve().toBodilessEntity();
    }
}
