package org.deathdric.application1.data.model;

import java.math.BigDecimal;

public record OrderItemWithFullProduct(
        Integer quantity,
        Integer productId,
        String productName,
        String productDescription,
        BigDecimal productPrice) {
}
