package com.unknown.commerceserver.domain.order.application;

import com.unknown.commerceserver.domain.item.dao.ItemProductRepository;
import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import com.unknown.commerceserver.domain.order.dao.OrderItemRepository;
import com.unknown.commerceserver.domain.order.dao.OrderRepository;
import com.unknown.commerceserver.domain.order.dto.request.OrderItemRequest;
import com.unknown.commerceserver.domain.order.dto.request.OrderRequest;
import com.unknown.commerceserver.domain.order.dto.response.OrderItemResponse;
import com.unknown.commerceserver.domain.order.dto.response.OrderListResponse;
import com.unknown.commerceserver.domain.order.dto.response.OrderResponse;
import com.unknown.commerceserver.domain.order.entity.Order;
import com.unknown.commerceserver.domain.order.entity.OrderItem;
import com.unknown.commerceserver.domain.order.enumerated.DeliveryStatus;
import com.unknown.commerceserver.domain.product.dao.ProductRepository;
import com.unknown.commerceserver.domain.product.entity.Product;
import com.unknown.commerceserver.global.exception.BusinessException;
import com.unknown.commerceserver.global.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final ItemProductRepository itemProductRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // 주문 초기화
        Order order = Order.builder()
                .contact(orderRequest.getContact())
                .address(orderRequest.getAddress())
                .totalPrice(BigDecimal.ZERO)
                .build();
        orderRepository.save(order);

        // 주문 품목
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItemList()) {
            // 상품 확인
            Item item = itemRepository.findById(orderItemRequest.getItemId())
                    .orElseThrow(() -> BusinessException.builder()
                            .response(HttpResponse.Fail.NOT_FOUND_ITEM).build());

            // 상품 - 제품 관계 확인
            List<ItemProduct> itemProducts = itemProductRepository.findByItemId(item.getId());
            BigDecimal calculatedItemPrice = BigDecimal.ZERO;
            for (ItemProduct itemProduct : itemProducts) {
                Product product = itemProduct.getProduct();

                // 제품 재고 확인
                Long requiredQuantity = itemProduct.getQuantity() * orderItemRequest.getQuantity();
                if (product.getStock() < requiredQuantity) { // 재고 < 필요수량
                    throw new BusinessException(HttpResponse.Fail.OUT_OF_ITEM_STOCK);
                }

                // (product)제품 가격 * (itemProduct)수량, 상품별 가격
                BigDecimal itemCost = product.getProductPrice().multiply(BigDecimal.valueOf(itemProduct.getQuantity()));
                calculatedItemPrice = calculatedItemPrice.add(itemCost);

                // 재고 감소
                product.setStock(product.getStock() - requiredQuantity);
                productRepository.save(product);
            }

            // 주문 아이템 저장
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .item(item)
                    .quantity(orderItemRequest.getQuantity())
                    .itemPrice(item.getItemPrice())
                    .itemName(item.getName())
                    .build();
            orderItemRepository.save(orderItem);
            orderItemList.add(orderItem);
        }

        // 총 금액 계산
        BigDecimal totalPrice = getTotalPrice(orderItemList);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        order.setStatus(DeliveryStatus.ACCEPTED);

        // 주문 응답 생성
        List<OrderItemResponse> orderItemResponses = createOrderItemResponses(orderItemList);

        return OrderResponse.of(order, orderItemResponses, totalPrice);
    }

    @Override
    public BigDecimal getTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItemList) {
            BigDecimal total = orderItem.getItemPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            totalPrice = totalPrice.add(total);
        }

        return totalPrice;
    }

    @Override
    public List<OrderItemResponse> createOrderItemResponses(List<OrderItem> orderItemList) {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            OrderItemResponse orderItemResponse = OrderItemResponse.of(orderItem);
            orderItemResponses.add(orderItemResponse);
        }

        return orderItemResponses;
    }

    @Override
    public List<OrderListResponse> getOrders() {
        return orderRepository.findAllAndDeletedAtIsNull().stream()
                .map(OrderListResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findByIdAndDeletedAtIsNull(orderId)
                .orElseThrow(() -> new BusinessException(HttpResponse.Fail.NOT_FOUND_ORDER));
        List<OrderItemResponse> orderItemList = orderItemRepository.findByOrderId(orderId);
        BigDecimal totalPrice = order.getTotalPrice();

        return OrderResponse.of(order, orderItemList, totalPrice);
    }
}
