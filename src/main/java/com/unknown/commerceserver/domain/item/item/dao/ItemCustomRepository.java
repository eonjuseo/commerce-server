package com.unknown.commerceserver.domain.item.item.dao;

import com.unknown.commerceserver.domain.item.item.entity.Item;

import java.util.List;

public interface ItemCustomRepository {
    List<Item> findAllAndDeletedAtIsNull();
}
