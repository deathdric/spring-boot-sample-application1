package org.deathdric.application1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Jacksonized
public class OrderDto {
    private final Integer id;
    private final String clientId;
    private final boolean active;
    private final List<OrderItemDto> items;
    private final BigDecimal totalPrice;

}
