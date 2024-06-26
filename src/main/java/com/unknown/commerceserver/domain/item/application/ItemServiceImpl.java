package com.unknown.commerceserver.domain.item.application;

import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.dto.ItemDetailResponse;
import com.unknown.commerceserver.domain.item.dto.ItemListResponse;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.global.exception.BusinessException;
import com.unknown.commerceserver.global.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<ItemListResponse> getItems() {
        return itemRepository.findAllAndDeletedAtIsNull().stream()
                .map(ItemListResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDetailResponse getItem(Long itemId) {
        Item item = itemRepository.findByIdAndDeletedAtIsNull(itemId)
                .orElseThrow(() -> new BusinessException(HttpResponse.Fail.NOT_FOUND_ITEM));
        return ItemDetailResponse.of(item);
    }
}
