package com.unknown.commerceserver.domain.order.dao;

import com.unknown.commerceserver.domain.order.entity.OrderItem;
import com.unknown.commerceserver.domain.order.dto.response.OrderItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItemResponse> findByOrderId(Long orderId);
}
