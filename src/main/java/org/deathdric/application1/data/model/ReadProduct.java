package org.deathdric.application1.data.model;

import java.math.BigDecimal;

public record ReadProduct (Integer id, String name, String description, BigDecimal price) {
}
