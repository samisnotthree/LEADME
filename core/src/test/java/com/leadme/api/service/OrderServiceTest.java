package com.leadme.api.service;

import com.leadme.api.entity.Member;
import com.leadme.api.entity.Orders;
import com.leadme.api.entity.ProgDaily;
import com.leadme.api.entity.enums.OrderStatus;
import com.leadme.api.repository.order.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired ProgDailyService progDailyService;

    @Test
    @DisplayName("주문 등록")
    @Transactional
    void add_order() {
        //given
        Orders order = Orders.builder()
            .price(50000L)
            .payment("카드")
            .orderDate(LocalDateTime.now())
            .payDate(LocalDateTime.now())
            .status(OrderStatus.PAYED)
            .build();

        //when
        Long addedOrder = orderService.addOrder(order);

        //then
        Orders foundOrder = orderRepository.findById(addedOrder).get();
        assertThat(foundOrder).isSameAs(order);
    }

    @Test
    @DisplayName("주문 취소")
    @Transactional
    void cancel_order() {
        //given
        ProgDaily progDaily = ProgDaily.builder()
                .progDate(LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
                .build();
        progDailyService.addProgDaily(progDaily);

        Orders order = Orders.builder()
            .price(50000L)
            .payment("카드")
            .orderDate(LocalDateTime.now())
            .payDate(LocalDateTime.now())
            .status(OrderStatus.PAYED)
            .progDaily(progDaily)
            .build();
        Long addedOrder = orderService.addOrder(order);
        Orders foundOrder = orderRepository.findById(addedOrder).get();

        //when
        orderService.cancelOrder(foundOrder);

        //then
        assertThat(foundOrder.getStatus()).isSameAs(OrderStatus.REFUNDED);
    }

    @Test
    @DisplayName("주문 취소 시작 한시간 이하 전")
    @Transactional
    void cancel_order_one_hour() {
        //given
        ProgDaily progDaily = ProgDaily.builder()
                .progDate(LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
                .build();
        progDailyService.addProgDaily(progDaily);

        Orders order = Orders.builder()
            .price(50000L)
            .payment("카드")
            .orderDate(LocalDateTime.now())
            .payDate(LocalDateTime.now())
            .status(OrderStatus.PAYED)
            .progDaily(progDaily)
            .build();
        Long addedOrder = orderService.addOrder(order);
        Orders foundOrder = orderRepository.findById(addedOrder).get();

        //when
        Throwable exception = catchThrowable(() -> {
            orderService.cancelOrder(foundOrder);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("환불 가능 기간이 지났습니다.");
    }
}
