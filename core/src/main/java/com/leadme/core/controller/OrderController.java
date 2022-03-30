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
    
    @Transactional
    @DeleteMapping("/order")
    public void cancelOrder(@RequestBody OrderDto orderDto) {
        orderService.cancelOrder(orderDto.toEntity());
    }
}
