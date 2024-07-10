package com.unknown.commerceserver.domain.order.application;

import com.unknown.commerceserver.domain.order.dto.request.OrderRequest;
import com.unknown.commerceserver.domain.order.dto.response.OrderListResponse;
import com.unknown.commerceserver.domain.order.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    List<OrderListResponse> getOrders();
    OrderResponse getOrder(Long orderId);
}
