package com.leadme.core.service;

import com.leadme.core.entity.Orders;
import com.leadme.core.entity.enums.OrderStatus;
import com.leadme.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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
}
