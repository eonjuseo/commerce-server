package com.unknown.commerceserver.domain.item.dao;

import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemProductRepository extends JpaRepository<ItemProduct, Long> {
    List<ItemProduct> findByItemId(Long itemId);
}
