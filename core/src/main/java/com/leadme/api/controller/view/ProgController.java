package com.leadme.api.controller.view;

import com.leadme.api.dto.ProgDto;
import com.leadme.api.repository.prog.ProgQueryRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.service.ProgHashtagService;
import com.leadme.api.service.ProgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProgController {
    private final ProgService progService;
    private final ProgRepository progRepository;
    private final ProgHashtagService progHashtagService;
    private final ProgQueryRepository progQueryRepository;

    @GetMapping("/prog/v2")
    public String createForm(Model model) {
        model.addAttribute("progForm", new ProgDto());
        return "progs/progForm";
    }

    @Transactional
    @PostMapping("/progs/v2")
    public String create(@Valid ProgDto progDto, BindingResult result) {
        if (result.hasErrors()) {
            return "progs/progForm";
        }
        progService.addProg(progDto.toEntity());

        return "redirect:/";
    }
}
