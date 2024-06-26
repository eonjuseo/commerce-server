package com.unknown.commerceserver.domain.order.dto.response;

import com.unknown.commerceserver.domain.order.entity.Order;
import com.unknown.commerceserver.domain.order.enumerated.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderListResponse {
    private Long id;
    private BigDecimal totalPrice;
    private String address;
    private Long contact;
    private DeliveryStatus status;

    public static OrderListResponse of (Order order) {
        return OrderListResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .address(order.getAddress())
                .contact(order.getContact())
                .status(order.getStatus())
                .build();
    }
}
