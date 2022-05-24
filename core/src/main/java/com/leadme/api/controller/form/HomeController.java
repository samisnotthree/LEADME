package com.leadme.api.controller.form;

import com.leadme.api.dto.condition.ProgSearchCondition;
import com.leadme.api.dto.sdto.ProgGuideMemberDto;
import com.leadme.api.repository.prog.ProgQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final ProgQueryRepository progQueryRepository;

    @RequestMapping("/")
    public String home(Model model) {
        ProgSearchCondition condition = new ProgSearchCondition();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("orderId").descending());

        List<ProgGuideMemberDto> progs = progQueryRepository.searchProgs(condition, pageable).getContent();
        model.addAttribute("progs", progs);

        return "home";
    }
}