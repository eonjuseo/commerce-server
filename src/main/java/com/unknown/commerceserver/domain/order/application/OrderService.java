package com.unknown.commerceserver.domain.order.application;

import com.unknown.commerceserver.domain.order.dto.response.OrderListResponse;
import com.unknown.commerceserver.domain.order.dto.response.OrderResponse;
import com.unknown.commerceserver.domain.order.dto.response.OrderItemResponse;
import com.unknown.commerceserver.domain.order.dto.request.OrderRequest;
import com.unknown.commerceserver.domain.order.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    BigDecimal getTotalPrice(List<OrderItem> orderItemList);

    List<OrderItemResponse> createOrderItemResponses(List<OrderItem> orderItemList);

    List<OrderListResponse> getOrders();
    OrderResponse getOrder(Long orderId);
}
