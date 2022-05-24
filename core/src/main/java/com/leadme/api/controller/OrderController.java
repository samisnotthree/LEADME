package com.leadme.api.controller;

import com.leadme.api.dto.OrderDto;
import com.leadme.api.dto.condition.OrderSearchCondition;
import com.leadme.api.dto.sdto.OrderProgDailyDto;
import com.leadme.api.entity.Orders;
import com.leadme.api.repository.order.OrderQueryRepository;
import com.leadme.api.repository.order.OrderRepository;
import com.leadme.api.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    @PostMapping("/orders")
    public Long addOrder(@RequestBody OrderDto orderDto) {
        return orderService.addOrder(orderDto.toEntity());
    }

    @GetMapping("/orders")
    public Result findAll() {
        return new Result(
                orderRepository.findAll()
                        .stream()
                        .map(OrderDto::new)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/orders/guide/{content}")
    public Page<OrderProgDailyDto> searchOrdersByProgDailyId(@PathVariable("content") Long progDailyId, Pageable pageable) {
        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setProgDailyId(progDailyId);
        return orderQueryRepository.searchOrdersByProgDailyId(condition, pageable);
    }

    @GetMapping("/orders/mypage/{content}")
    public Page<OrderProgDailyDto> searchOrdersByMemberId(@PathVariable("content") Long memberId, Pageable pageable) {
        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setMemberId(memberId);
        return orderQueryRepository.searchOrdersByMemberId(condition, pageable);
    }

    @GetMapping("/orders/admin/{content}")
    public Page<OrderProgDailyDto> searchOrdersByCondition(@PathVariable("content") String content, Pageable pageable) {
        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setName(content);
        condition.setEmail(content);
        condition.setProgName(content);
        return orderQueryRepository.searchOrdersByCondition(condition, pageable);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T orders;
    }

    @Transactional
    @PatchMapping("/orders")
    public void changeOrder(@RequestBody OrderDto orderDto) {
        Optional<Orders> order = orderRepository.findById(orderDto.getOrderId());
        order.ifPresent(o -> o.changeOrderInfo(orderDto.toEntity()));
    }

    @Transactional
    @DeleteMapping("/orders")
    public void cancelOrder(@RequestBody OrderDto orderDto) {
        orderService.cancelOrder(orderDto.toEntity());
    }
}
