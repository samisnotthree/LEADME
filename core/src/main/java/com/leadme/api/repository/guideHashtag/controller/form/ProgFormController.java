package com.leadme.api.repository.guideHashtag.controller.form;

import com.leadme.api.repository.prog.ProgQueryRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.service.ProgHashtagService;
import com.leadme.api.service.ProgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProgFormController {
    private final ProgService progService;
    private final ProgRepository progRepository;
    private final ProgHashtagService progHashtagService;
    private final ProgQueryRepository progQueryRepository;
}
