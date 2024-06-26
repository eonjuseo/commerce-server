package com.unknown.commerceserver.domain.order.dto.request;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OrderItemRequest {
    private Long itemId;
    private Long quantity;
}
