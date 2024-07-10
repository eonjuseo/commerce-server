package com.unknown.commerceserver.domain.order.entity;

import com.unknown.commerceserver.domain.order.enumerated.DeliveryStatus;
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
@Table(name = "orders")
public class Order extends BaseEntity {
    @Comment("전체 가격")
    @Column(name = "total_price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal totalPrice;

    @Comment("고객 주소")
    @Column(name = "address", columnDefinition = "VARCHAR(200)")
    private String address;

    @Comment("고객 연락처")
    @Column(name = "contact", columnDefinition = "BIGINT")
    private Long contact;

    @Comment("배송 상태")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20)")
    private DeliveryStatus status;

    public void saveTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void acceptOrder() {
        this.status = DeliveryStatus.ACCEPTED;
    }
}
