package com.unknown.commerceserver.domain.item.application;

import com.unknown.commerceserver.domain.item.dto.ItemDetailResponse;
import com.unknown.commerceserver.domain.item.dto.ItemListResponse;

import java.util.List;

public interface ItemService {
    List<ItemListResponse> getItems();
    ItemDetailResponse getItem(Long itemId);
}
