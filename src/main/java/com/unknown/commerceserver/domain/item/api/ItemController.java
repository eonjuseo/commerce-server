package com.unknown.commerceserver.domain.item.api;

import com.unknown.commerceserver.domain.item.dto.ItemDetailResponse;
import com.unknown.commerceserver.domain.item.dto.ItemListResponse;
import com.unknown.commerceserver.domain.item.application.ItemService;
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
        ItemDetailResponse itemDetailResponse = itemService.getItem(itemId);
        return ResponseEntity.ok().body(itemDetailResponse);
    }
}
