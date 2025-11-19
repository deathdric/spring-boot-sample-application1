package org.deathdric.application1.service;

import lombok.RequiredArgsConstructor;
import org.deathdric.application1.data.dao.OrderDao;
import org.deathdric.application1.data.model.FullOrder;
import org.deathdric.application1.data.model.InsertItemNewOrder;
import org.deathdric.application1.data.model.InsertOrder;
import org.deathdric.application1.web.dto.OrderCreationRequest;
import org.deathdric.application1.web.dto.OrderDto;
import org.deathdric.application1.web.dto.OrderItemDto;
import org.deathdric.application1.web.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;


    public OrderDto createOrder(OrderCreationRequest orderCreationRequest) {
        var newOrder = new InsertOrder(orderCreationRequest.getClientId());
        var newOrderItems = Optional.ofNullable(orderCreationRequest.getItems()).orElse(new ArrayList<>()).stream().map(
                it -> new InsertItemNewOrder(it.getProduct().getProductId(), it.getQuantity())
        ).toList();
        var orderId = orderDao.insertOrder(newOrder, newOrderItems);
        var savedOrder = orderDao.fetchOrder(orderId);

        return mapToOrderDto(savedOrder);
    }

    public Optional<OrderDto> getOrder(Integer orderId) {
        return Optional.ofNullable(orderDao.fetchOrder(orderId)).map(this::mapToOrderDto);
    }

    private OrderDto mapToOrderDto(FullOrder order) {
         return OrderDto.builder()
                .id(order.order().orderId())
                .active(order.order().active())
                .clientId(order.order().client())
                .items(order.items().stream().map(
                                it -> OrderItemDto.builder()
                                        .quantity(it.quantity())
                                        .product(ProductDto.builder()
                                                .productId(it.productId())
                                                .name(it.productName())
                                                .description(it.productDescription())
                                                .price(it.productPrice())
                                                .build())
                                        .build())
                        .toList()
                )
                .totalPrice(computeTotalPrice(order))
                .build();
    }

    public BigDecimal computeTotalPrice(FullOrder order) {
        var priceSum = BigDecimal.ZERO;
        for (var orderItem : order.items()) {
            var initialPrice = orderItem.productPrice();
            priceSum = priceSum.add(initialPrice.multiply(BigDecimal.valueOf(orderItem.quantity()), MathContext.DECIMAL64), MathContext.DECIMAL64);
        }
        return priceSum;

    }
}
