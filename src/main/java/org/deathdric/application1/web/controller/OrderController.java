package org.deathdric.application1.web.controller;

import lombok.RequiredArgsConstructor;
import org.deathdric.application1.service.OrderService;
import org.deathdric.application1.web.dto.OrderCreationRequest;
import org.deathdric.application1.web.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public OrderDto createOrder(@RequestBody OrderCreationRequest orderCreationRequest) {
        return orderService.createOrder(orderCreationRequest);
    }

    @GetMapping("/api/order/id/{orderId}")
    public ResponseEntity<OrderDto> fetchOrder(@PathVariable Integer orderId) {
        return ResponseEntity.of(orderService.getOrder(orderId));
    }
}
