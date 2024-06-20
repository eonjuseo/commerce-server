package com.unknown.commerceserver.domain.item.itemProduct.entity;

import com.unknown.commerceserver.domain.item.item.entity.Item;
import com.unknown.commerceserver.domain.product.entity.Product;
import com.unknown.commerceserver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "item_product")
public class ItemProduct extends BaseEntity {
    @Comment("상품")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @Comment("제품")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Product product;

    @Comment("상품 수량")
    @Column(name = "quantity", columnDefinition = "BIGINT")
    private Long quantity;

}
