package com.leadme.core.service;

import com.leadme.core.entity.Orders;
import com.leadme.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Long addOrder(Orders order) {

        return 1L;
    }
}
