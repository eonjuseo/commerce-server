package com.unknown.commerceserver.domain.item.item.entity;

import com.unknown.commerceserver.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "items")
public class Item extends BaseEntity {
    @Comment("상품명")
    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Comment("상품 가격")
    @Column(name = "item_price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal itemPrice;
}
