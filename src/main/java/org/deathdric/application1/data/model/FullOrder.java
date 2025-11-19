package org.deathdric.application1.data.model;

import java.util.List;

public record FullOrder(ReadOrder order, List<OrderItemWithFullProduct> items) {
}
