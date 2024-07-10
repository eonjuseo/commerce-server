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
            // 주문 항목에 포함된 상품이 존재하는지 확인
            Item item = itemRepository.findById(orderItemRequest.getItemId())
                    .orElseThrow(() -> new BusinessException(HttpResponse.Fail.NOT_FOUND_ITEM));

            // 상품에 연결된 제품 목록 가져옴
            List<ItemProduct> itemProducts = itemProductRepository.findByItemId(item.getId());
            for (ItemProduct itemProduct : itemProducts) {
                Product product = productRepository.findProductWithLock(itemProduct.getProduct().getId());
                // 필요한 수량만큼 제품의 재고가 있는지 확인
                Long requiredQuantity = itemProduct.getQuantity() * orderItemRequest.getQuantity();
                if (product.getStock() < requiredQuantity) { // 재고 < 필요수량
                    throw new BusinessException(HttpResponse.Fail.OUT_OF_ITEM_STOCK);
                }

                // 필요한 수량만큼 재고 감소시키고 저장
                product.setStock(product.getStock() - requiredQuantity);
                productRepository.save(product);
            }

            // 주문 상품 저장
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

        // 주문 상태 변경
        order.setStatus(DeliveryStatus.ACCEPTED);

        // 주문 응답 생성
        List<OrderItemResponse> orderItemResponses = createOrderItemResponses(orderItemList);

        return OrderResponse.of(order, orderItemResponses, totalPrice);
    }

   private BigDecimal getTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItemList) {
            BigDecimal total = orderItem.getItemPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            totalPrice = totalPrice.add(total);
        }

        return totalPrice;
    }

    // 주문 항목 응답 객체들을 생성하여 리스트로 반환
   private List<OrderItemResponse> createOrderItemResponses(List<OrderItem> orderItemList) {
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

        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
        List<OrderItemResponse> orderItemResponses = createOrderItemResponses(orderItemList);
        BigDecimal totalPrice = order.getTotalPrice();

        return OrderResponse.of(order, orderItemResponses, totalPrice);
    }
}
