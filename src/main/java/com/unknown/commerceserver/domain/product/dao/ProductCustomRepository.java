package com.unknown.commerceserver.domain.product.dao;

import com.unknown.commerceserver.domain.product.entity.Product;

public interface ProductCustomRepository {
    Product findProductWithLock(Long id);

}
