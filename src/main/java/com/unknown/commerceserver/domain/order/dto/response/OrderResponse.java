package com.unknown.commerceserver.domain.order.dto.response;

import com.unknown.commerceserver.domain.order.entity.Order;
import com.unknown.commerceserver.domain.order.enumerated.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private BigDecimal totalPrice;
    private String address;
    private Long contact;
    private DeliveryStatus status;
    private List<OrderItemResponse> orderItemList;

    public static OrderResponse of(Order order, List<OrderItemResponse> orderItemList, BigDecimal totalPrice) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(totalPrice)
                .address(order.getAddress())
                .contact(order.getContact())
                .status(order.getStatus())
                .orderItemList(orderItemList)
                .build();
    }
}
