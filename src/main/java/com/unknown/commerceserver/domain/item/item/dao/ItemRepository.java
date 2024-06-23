package com.unknown.commerceserver.domain.item.item.dao;

import com.unknown.commerceserver.domain.item.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
