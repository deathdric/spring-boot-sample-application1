package org.deathdric.application1.data.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.deathdric.application1.data.model.ReadProduct;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_ALL_PRODUCTS = "SELECT id, name, description, price FROM products";

    public List<ReadProduct> findAllProduts() {
        return jdbcTemplate.query(GET_ALL_PRODUCTS, this::mapProduct);
    }

    @SneakyThrows
    private ReadProduct mapProduct(ResultSet resultSet, int rowNum) {
        return new ReadProduct(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBigDecimal("price")
        );
    }
}
