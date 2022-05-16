package com.leadme.api.service;

import com.leadme.api.entity.Orders;
import com.leadme.api.entity.enums.OrderStatus;
import com.leadme.api.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Long addOrder(Orders order) {
        if (!validateOrder(order)) {
            throw new IllegalStateException("정원 초과입니다.");
        }

        //TODO 결제 분리 시 결제 일시 로직 수정
        order.successPayed();

        return orderRepository.save(order).getOrderId();
    }

    public boolean validateOrder(Orders order) {
        List<Orders> foundOrders = orderRepository.findByProgDaily(order.getProgDaily());
        if (foundOrders.isEmpty()) {
            return true;
        }
        return foundOrders.size() < foundOrders.get(0).getProgDaily().getProg().getMaxMember();
    }
    
    @Transactional
    public void cancelOrder(Orders order) {
        if (!canCancelOrder(order)) {
            throw new IllegalStateException("환불 가능 기간이 지났습니다.");
        }
        order.changeStatus(OrderStatus.REFUNDED);
    }
    
    /**
    * 프로그램 시작 한시간 전까지 취소 가능
    */
    public boolean canCancelOrder(Orders order) {
        return Long.parseLong(order.getProgDaily().getProgDate()) > Long.parseLong(LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
    }
}
