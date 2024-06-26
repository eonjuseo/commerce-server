package com.unknown.commerceserver.domain.order.dao;

import com.unknown.commerceserver.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
    Optional<Order> findByIdAndDeletedAtIsNull(Long id);
}
