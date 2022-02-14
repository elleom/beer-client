package com.lm.beerclient.model;

import com.lm.beerclient.model.v2.BeerStyle;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author el_le
 * @since 09/12/2021 15:26
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Null
    private UUID id;

    @NotBlank
    private String beerName;

    @NotBlank
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastUpdatedDate;



}
