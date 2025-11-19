package org.deathdric.application1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@AllArgsConstructor
@Builder
@Jacksonized
public class OrderItemDto {
    private final ProductDto product;
    private final Integer quantity;
}
