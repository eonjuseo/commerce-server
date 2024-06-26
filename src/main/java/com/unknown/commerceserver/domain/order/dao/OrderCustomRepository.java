package com.unknown.commerceserver.domain.order.dao;

import com.unknown.commerceserver.domain.order.entity.Order;

import java.util.List;

public interface OrderCustomRepository {
    List<Order> findAllAndDeletedAtIsNull();
}
