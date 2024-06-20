package com.unknown.commerceserver.domain.order.orderitem.entity;

import com.unknown.commerceserver.domain.order.order.entity.Order;
import com.unknown.commerceserver.domain.item.item.entity.Item;
import com.unknown.commerceserver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
    @Comment("주문")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @Comment("상품")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @Comment("상품 수량")
    @Column(name = "quantity", columnDefinition = "BIGINT")
    private Long quantity;

    @Comment("상품 이름")
    @Column(name = "item_name", columnDefinition = "VARCHAR(50)")
    private String itemName;

    @Comment("상품 가격")
    @Column(name = "item_price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal itemPrice;
}
