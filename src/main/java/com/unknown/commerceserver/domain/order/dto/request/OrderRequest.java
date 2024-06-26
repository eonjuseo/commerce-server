package com.unknown.commerceserver.domain.order.dto.request;

import com.unknown.commerceserver.domain.order.dto.request.OrderItemRequest;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OrderRequest {
    private Long contact;
    private String address;
    private List<OrderItemRequest> orderItemList;
}
