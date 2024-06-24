package com.unknown.commerceserver.domain.item.item.application;

import com.unknown.commerceserver.domain.item.item.dto.ItemDetailResponse;
import com.unknown.commerceserver.domain.item.item.dto.ItemListResponse;

import java.util.List;

public interface ItemService {
    List<ItemListResponse> getItems();
    ItemDetailResponse getItem(Long itemId);
}
