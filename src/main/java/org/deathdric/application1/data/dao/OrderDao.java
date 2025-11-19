package org.deathdric.application1.data.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.deathdric.application1.data.model.FullOrder;
import org.deathdric.application1.data.model.InsertOrder;
import org.deathdric.application1.data.model.InsertItemNewOrder;
import org.deathdric.application1.data.model.OrderItemWithFullProduct;
import org.deathdric.application1.data.model.ReadOrder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_ORDER_SQL = "INSERT INTO orders(client_id, active) VALUES (:clientId, TRUE)";
    private static final String INSERT_ORDER_ITEM_SQL = "INSERT INTO order_items(order_id, product_id, quantity) VALUES (:orderId, :id, :quantity)";
    private static final String GET_ORDER_SQL = "SELECT id, client_id, active FROM orders WHERE id = :orderId";
    private static final String GET_ORDER_ITEMS_SQL = """
    SELECT order_items.product_id as id,
    order_items.quantity,
    products.name as productName,
    products.description as productDescription,
    products.price as productPrice
    FROM order_items INNER JOIN products ON order_items.product_id = products.id
    WHERE order_items.order_id = :orderId
    """;


    public Integer insertOrder(InsertOrder insertOrder, List<InsertItemNewOrder> insertOrderItems) {
        var keyHolder = new GeneratedKeyHolder();
        var orderParameterSource = new MapSqlParameterSource();
        orderParameterSource.addValue("clientId", insertOrder.clientId());
        jdbcTemplate.update(INSERT_ORDER_SQL, orderParameterSource, keyHolder, new String[]{"id"});
        var orderId = keyHolder.getKey().intValue();
        if (!insertOrderItems.isEmpty()) {
            var batchParams = insertOrderItems.stream()
                    .map(it -> new MapSqlParameterSource()
                            .addValue("orderId", orderId)
                            .addValue("id", it.productId())
                            .addValue("quantity", it.quantity()))
                    .toList();
            jdbcTemplate.batchUpdate(INSERT_ORDER_ITEM_SQL, batchParams.toArray(new MapSqlParameterSource[0]));
        }
        return orderId;
    }

    public FullOrder fetchOrder(Integer orderId) {
        var orderParameterSource = new MapSqlParameterSource();
        orderParameterSource.addValue("orderId", orderId);
        var orderList = jdbcTemplate.query(
                GET_ORDER_SQL, orderParameterSource, this::mapOrder
        );
        if (orderList.isEmpty()) {
            return null;
        }
        var order = orderList.getFirst();
        var orderItemList = jdbcTemplate.query(
                GET_ORDER_ITEMS_SQL, orderParameterSource, this::mapOrderItem
        );
        return new FullOrder(order, orderItemList);
    }

    @SneakyThrows
    private ReadOrder mapOrder(ResultSet rs, int rowNum) {
        return new ReadOrder(rs.getInt("id"), rs.getString("client_id"),
                rs.getBoolean("active"));
    }

    @SneakyThrows
    private OrderItemWithFullProduct mapOrderItem(ResultSet rs, int rowNum) {
        return new OrderItemWithFullProduct(
               rs.getInt("quantity"),
               rs.getInt("id"),
               rs.getString("productName"),
               rs.getString("productDescription"),
               rs.getBigDecimal("productPrice")
        );
    }
}
