package com.leadme.core.service;

import com.leadme.core.entity.Member;
import com.leadme.core.entity.Orders;
import com.leadme.core.entity.Prog;
import com.leadme.core.entity.ProgDaily;
import com.leadme.core.entity.enums.OrderStatus;
import com.leadme.core.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @DisplayName("주문 등록")
    @Transactional
    void addOrderTest() {
        //given
        Member member = new Member("test@test.com", "testName", "testPass", "testPhone", "testPhoto", LocalDateTime.now(),null,null);

        Orders order = new Orders(1000L, "카드", LocalDateTime.now(), LocalDateTime.now(), OrderStatus.PAYED, member, null);

        //when
        Long addedOrder = orderService.addOrder(order);

        Orders foundOrder = orderRepository.findById(addedOrder).get();

        //then
        Assertions.assertThat(foundOrder).isSameAs(order);
    }
}