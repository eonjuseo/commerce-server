package com.unknown.commerceserver.domain.item.item.application;

import com.unknown.commerceserver.domain.item.item.dto.ItemListResponse;
import com.unknown.commerceserver.domain.item.item.entity.Item;

import java.util.List;

public interface ItemService {
    List<ItemListResponse> getItems();
    Item getItem(Long itemId);
}
