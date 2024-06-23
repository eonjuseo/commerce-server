package com.unknown.commerceserver.global.init;

import com.unknown.commerceserver.domain.item.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.item.entity.Item;
import com.unknown.commerceserver.domain.item.itemProduct.dao.ItemProductRepository;
import com.unknown.commerceserver.domain.item.itemProduct.entity.ItemProduct;
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

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            if (!itemRepository.findAll().isEmpty()) {
                return;
            }

            initItem();
            initProduct();
            initItemProduct();
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

}
