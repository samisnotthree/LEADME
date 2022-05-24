package com.leadme.api.dummy;

import com.leadme.api.entity.Member;
import com.leadme.api.entity.Orders;
import com.leadme.api.entity.ProgDaily;
import com.leadme.api.entity.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderDummy {

    public static Orders createOrder(Member member, ProgDaily progDaily) {
        return Orders.builder()
            .price(18000L)
            .status(OrderStatus.PAYED)
            .payment("네이버페이")
            .orderDate(LocalDateTime.now())
            .payDate(LocalDateTime.now())
            .member(member)
            .progDaily(progDaily)
            .build();
    }
}
