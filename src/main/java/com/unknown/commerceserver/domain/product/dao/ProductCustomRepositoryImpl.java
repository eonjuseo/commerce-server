package com.unknown.commerceserver.domain.product.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unknown.commerceserver.domain.product.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Repository;

import static com.unknown.commerceserver.domain.product.entity.QProduct.product;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    private final JPAQueryFactory queryFactory;

    public ProductCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Product findProductWithLock(Long id) {
        return queryFactory.selectFrom(product)
                .where(product.id.eq(id)
                        .and(product.deletedAt.isNull()))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .setHint("javax.persistence.lock.timeout", 5000) // 5초 타임아웃 설정
                .fetchOne();
    }
}
