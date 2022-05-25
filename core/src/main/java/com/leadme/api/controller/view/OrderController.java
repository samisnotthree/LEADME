package com.leadme.api.controller.view;

import com.leadme.api.repository.order.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderQueryRepository orderQueryRepository;
}
