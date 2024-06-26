package com.unknown.commerceserver.domain.order.dto.response;

import com.unknown.commerceserver.domain.order.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemResponse {
    private Long id;
    private Long itemId;
    private Long quantity;
    private BigDecimal itemPrice;
    private String itemName;

    public static OrderItemResponse of (OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .itemId(orderItem.getItem().getId())
                .quantity(orderItem.getQuantity())
                .itemPrice(orderItem.getItemPrice())
                .itemName(orderItem.getItemName())
                .build();
    }
}
