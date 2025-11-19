package org.deathdric.application1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Jacksonized
public class OrderCreationRequest {
    private final String clientId;
    private final List<OrderItemRequestDto> items;
}
