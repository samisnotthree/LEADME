package com.leadme.api.controller;

import com.leadme.api.dto.OrderDto;
import com.leadme.api.repository.OrderRepository;
import com.leadme.api.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

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

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T orders;
    }
    @Transactional
    @DeleteMapping("/orders")
    public void cancelOrder(@RequestBody OrderDto orderDto) {
        orderService.cancelOrder(orderDto.toEntity());
    }
}
