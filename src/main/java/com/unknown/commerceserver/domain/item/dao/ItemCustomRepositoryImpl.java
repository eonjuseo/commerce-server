package com.unknown.commerceserver.domain.item.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unknown.commerceserver.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.unknown.commerceserver.domain.item.entity.QItem.item;

@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Item> findAllAndDeletedAtIsNull() {
        BooleanBuilder where = new BooleanBuilder();

        where.and(item.deletedAt.isNull());

        List<Item> itemList = jpaQueryFactory
                .select(item)
                .from(item)
                .where(where)
                .fetch();

        return itemList;
    }
}
