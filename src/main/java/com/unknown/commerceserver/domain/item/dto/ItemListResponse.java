package com.unknown.commerceserver.domain.item.dto;

import com.unknown.commerceserver.domain.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ItemListResponse {
    private Long id;
    private String name;
    private BigDecimal itemPrice;

    public static ItemListResponse of(Item item) {
        return ItemListResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .itemPrice(item.getItemPrice())
                .build();
    }
}
