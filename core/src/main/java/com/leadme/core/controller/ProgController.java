package com.leadme.core.controller;

import com.leadme.core.dto.MemberDto;
import com.leadme.core.dto.ProgDto;
import com.leadme.core.service.ProgService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProgController {
    private final ProgService progService;

    /**
     *  프로그램 등록
     */
    @Transactional
    @PostMapping("/addProg")
    public Long addProg(@RequestBody ProgDto progDto) {
        return progService.addProg(progDto.toEntity());
    }

    /**
     *  프로그램 조회
     */

    /**
     *  프로그램 상세 조회
     */
}
