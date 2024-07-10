package com.unknown.commerceserver.domain.product.entity;

import com.unknown.commerceserver.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {
    @Comment("제품명")
    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Comment("제품 가격")
    @Column(name = "product_price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal productPrice;

    @Comment("제품 재고")
    @Column(name = "stock", columnDefinition = "BIGINT")
    private Long stock;

    public void decreaseStock(Long quantity) {
        this.stock -= quantity;
    }
}
