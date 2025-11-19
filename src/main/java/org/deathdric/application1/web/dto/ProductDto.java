package org.deathdric.application1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
@Jacksonized
public class ProductDto {
    private final Integer productId;
    private final String name;
    private final String description;
    private final BigDecimal price;
}
