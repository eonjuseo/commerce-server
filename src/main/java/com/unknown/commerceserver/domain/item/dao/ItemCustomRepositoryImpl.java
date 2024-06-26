package com.unknown.commerceserver.domain.item.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.domain.item.entity.QItem;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Item> findAllAndDeletedAtIsNull() {
        BooleanBuilder where = new BooleanBuilder();

        where.and(QItem.item.deletedAt.isNull());

        List<Item> itemList = jpaQueryFactory
                .select(QItem.item)
                .from(QItem.item)
                .where(where)
                .fetch();

        return itemList;
    }
}
