package com.unknown.commerceserver.domain.order.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unknown.commerceserver.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.unknown.commerceserver.domain.order.entity.QOrder.order;

@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Order> findAllAndDeletedAtIsNull() {
        BooleanBuilder where = new BooleanBuilder();

        where.and(order.deletedAt.isNull());

        return jpaQueryFactory
                .select(order)
                .from(order)
                .where(where)
                .fetch();
    }
}
