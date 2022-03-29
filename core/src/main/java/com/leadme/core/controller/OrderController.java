package com.leadme.core.controller;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
  
    @Transactional
    @PostMapping("/order")
    public Long addOrder(@RequestBody OrderDto orderDto) {
        return orderService.addOrder(orderDto.toEntity());
    }
}
