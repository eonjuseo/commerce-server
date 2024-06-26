package com.unknown.commerceserver.domain.order.api;

import com.unknown.commerceserver.domain.order.dto.response.OrderListResponse;
import com.unknown.commerceserver.domain.order.dto.response.OrderResponse;
import com.unknown.commerceserver.domain.order.application.OrderService;
import com.unknown.commerceserver.domain.order.dto.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/orders")
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderListResponse>> getOrders() {
        List<OrderListResponse> orderListResponses = orderService.getOrders();
        return ResponseEntity.ok().body(orderListResponses);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        OrderResponse orderDetailResponse = orderService.getOrder(orderId);
        return ResponseEntity.ok().body(orderDetailResponse);
    }
}
