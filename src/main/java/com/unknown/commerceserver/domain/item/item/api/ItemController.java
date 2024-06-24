package com.unknown.commerceserver.domain.item.item.api;

import com.unknown.commerceserver.domain.item.item.application.ItemService;
import com.unknown.commerceserver.domain.item.item.dto.ItemDetailResponse;
import com.unknown.commerceserver.domain.item.item.dto.ItemListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/items")
@RestController
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemListResponse>> getItems() {
        List<ItemListResponse> itemListResponses = itemService.getItems();
        return ResponseEntity.ok().body(itemListResponses);
    }

    @GetMapping("{itemId}")
    public ResponseEntity<ItemDetailResponse> getItem(@PathVariable Long itemId) {
        ItemDetailResponse item = itemService.getItem(itemId);
        return ResponseEntity.ok().body(item);
    }
}
