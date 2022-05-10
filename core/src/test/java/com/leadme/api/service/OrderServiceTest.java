package com.leadme.api.service;

import com.leadme.api.entity.Member;
import com.leadme.api.entity.Orders;
import com.leadme.api.entity.enums.OrderStatus;
import com.leadme.api.repository.order.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @DisplayName("주문 등록")
    @Transactional
    void add_order() {
        //given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName")
            .pass("testPass")
            .phone("testPhone")
            .photo("testPhoto")
            .inDate(LocalDateTime.now())
            .outDate(null)
            .guide(null)
            .build();
        
        Orders order = Orders.builder()
            .price(50000L)
            .payment("카드")
            .orderDate(LocalDateTime.now())
            .payDate(LocalDateTime.now())
            .status(OrderStatus.PAYED)
            .member(member)
            .progDaily(null)
            .build();

        //when
        Long addedOrder = orderService.addOrder(order);

        //then
        Orders foundOrder = orderRepository.findById(addedOrder).get();
        Assertions.assertThat(foundOrder).isSameAs(order);
    }
}
