package com.unknown.commerceserver.domain.item.dto;

import com.unknown.commerceserver.domain.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ItemDetailResponse {
    private Long id;
    private String name;
    private BigDecimal itemPrice;

    public static ItemDetailResponse of(Item item) {
        return ItemDetailResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .itemPrice(item.getItemPrice())
                .build();
    }
}
