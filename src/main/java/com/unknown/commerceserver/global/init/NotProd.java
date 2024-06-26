package com.unknown.commerceserver.global.init;

import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.domain.item.dao.ItemProductRepository;
import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import com.unknown.commerceserver.domain.order.dao.OrderRepository;
import com.unknown.commerceserver.domain.order.entity.Order;
import com.unknown.commerceserver.domain.order.enumerated.DeliveryStatus;
import com.unknown.commerceserver.domain.order.dao.OrderItemRepository;
import com.unknown.commerceserver.domain.order.entity.OrderItem;
import com.unknown.commerceserver.domain.product.dao.ProductRepository;
import com.unknown.commerceserver.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Profile("!test")
@RequiredArgsConstructor
@Configuration
public class NotProd {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemProductRepository itemProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            if (!itemRepository.findAll().isEmpty()) {
                return;
            }

            initItem();
            initProduct();
            initItemProduct();
            initOrder();
            initOrderItem();
        };
    }

    private void initProduct() {
        productRepository.save(Product.builder().name("열라면").productPrice(BigDecimal.valueOf(1000)).stock(100L).build());
        productRepository.save(Product.builder().name("칸쵸").productPrice(BigDecimal.valueOf(1000)).stock(200L).build());
        productRepository.save(Product.builder().name("아이시스").productPrice(BigDecimal.valueOf(1500)).stock(50L).build());
        productRepository.save(Product.builder().name("김치볶음밥").productPrice(BigDecimal.valueOf(2000)).stock(100L).build());
        productRepository.save(Product.builder().name("잡채볶음밥").productPrice(BigDecimal.valueOf(2000)).stock(100L).build());
        productRepository.save(Product.builder().name("아이폰 16").productPrice(BigDecimal.valueOf(1000000)).stock(0L).build());
    }

    private void initItem() {
        itemRepository.save(Item.builder().name("열라면 5개입").itemPrice(BigDecimal.valueOf(5000)).build());
        itemRepository.save(Item.builder().name("아이시스 6개입").itemPrice(BigDecimal.valueOf(9000)).build());
        itemRepository.save(Item.builder().name("칸쵸 4개입").itemPrice(BigDecimal.valueOf(4000)).build());
        itemRepository.save(Item.builder().name("김치볶음밥 8개입").itemPrice(BigDecimal.valueOf(16000)).build());
        itemRepository.save(Item.builder().name("김치볶음밥, 잡채볶음밥 8개입").itemPrice(BigDecimal.valueOf(16000)).build());
        itemRepository.save(Item.builder().name("아이폰 16").itemPrice(BigDecimal.valueOf(1000000)).build());
    }

    private void initItemProduct() {
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findById(1L).get()).product(productRepository.findById(1L).get()).quantity(5L).build());
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findById(2L).get()).product(productRepository.findById(2L).get()).quantity(6L).build());
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findById(3L).get()).product(productRepository.findById(3L).get()).quantity(4L).build());
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findById(4L).get()).product(productRepository.findById(4L).get()).quantity(8L).build());
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findById(6L).get()).product(productRepository.findById(6L).get()).quantity(1L).build());

        Item item = itemRepository.findById(5L).get();
        itemProductRepository.save(ItemProduct.builder().item(item).product(productRepository.findById(4L).get()).quantity(4L).build());
        itemProductRepository.save(ItemProduct.builder().item(item).product(productRepository.findById(5L).get()).quantity(4L).build());
    }

    private void initOrder() {
        orderRepository.save(Order.builder().totalPrice(BigDecimal.valueOf(29000)).address("Seoul, Korea").contact(821012345678L).status(DeliveryStatus.ON_DELIVERY).build());
        orderRepository.save(Order.builder().totalPrice(BigDecimal.valueOf(41000)).address("Busan, Korea").contact(821098765432L).status(DeliveryStatus.DELIVERED).build());
        orderRepository.save(Order.builder().totalPrice(BigDecimal.valueOf(19000)).address("Incheon, Korea").contact(821011122233L).status(DeliveryStatus.ON_DELIVERY).build());
        orderRepository.save(Order.builder().totalPrice(BigDecimal.valueOf(10000000)).address("Daegu, Korea").contact(821022334455L).status(DeliveryStatus.DELIVERED).build());
        orderRepository.save(Order.builder().totalPrice(BigDecimal.valueOf(28000)).address("Daejeon, Korea").contact(821033445566L).status(DeliveryStatus.ACCEPTED).build());
    }

    private void initOrderItem() {
        List<Item> items = itemRepository.findAll();
        List<Order> orders = orderRepository.findAll();

        orderItemRepository.save(OrderItem.builder().order(orders.get(0)).item(items.get(0)).quantity(1L).itemName(items.get(0).getName()).itemPrice(items.get(0).getItemPrice()).build());
        orderItemRepository.save(OrderItem.builder().order(orders.get(0)).item(items.get(2)).quantity(1L).itemName(items.get(2).getName()).itemPrice(items.get(2).getItemPrice()).build());
        orderItemRepository.save(OrderItem.builder().order(orders.get(1)).item(items.get(3)).quantity(1L).itemName(items.get(3).getName()).itemPrice(items.get(3).getItemPrice()).build());
        orderItemRepository.save(OrderItem.builder().order(orders.get(1)).item(items.get(4)).quantity(1L).itemName(items.get(4).getName()).itemPrice(items.get(4).getItemPrice()).build());
        orderItemRepository.save(OrderItem.builder().order(orders.get(2)).item(items.get(0)).quantity(1L).itemName(items.get(0).getName()).itemPrice(items.get(0).getItemPrice()).build());
        orderItemRepository.save(OrderItem.builder().order(orders.get(2)).item(items.get(1)).quantity(1L).itemName(items.get(1).getName()).itemPrice(items.get(1).getItemPrice()).build());
        orderItemRepository.save(OrderItem.builder().order(orders.get(3)).item(items.get(5)).quantity(1L).itemName(items.get(5).getName()).itemPrice(items.get(5).getItemPrice()).build());
        orderItemRepository.save(OrderItem.builder().order(orders.get(4)).item(items.get(2)).quantity(1L).itemName(items.get(2).getName()).itemPrice(items.get(2).getItemPrice()).build());
        orderItemRepository.save(OrderItem.builder().order(orders.get(4)).item(items.get(3)).quantity(1L).itemName(items.get(3).getName()).itemPrice(items.get(3).getItemPrice()).build());
    }
}
