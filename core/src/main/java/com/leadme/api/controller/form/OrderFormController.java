package com.leadme.api.controller.form;

import com.leadme.api.dto.condition.OrderSearchCondition;
import com.leadme.api.dto.sdto.OrderProgDailyDto;
import com.leadme.api.repository.order.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderFormController {
    private final OrderQueryRepository orderQueryRepository;
}
