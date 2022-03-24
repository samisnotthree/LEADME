package com.leadme.core.controller;

import com.leadme.core.repository.ProgDailyRepository;
import com.leadme.core.repository.ProgRepository;
import com.leadme.core.service.ProgDailyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProgDailyController {
    private final ProgDailyService progDailyService;
    private final ProgDailyRepository progDailyRepository;
    private final ProgRepository progRepository;

    @Transactional
    @PostMapping("/addProgDaily")
    public Long addProgDaily(@RequestBody ProgDaily progDaily) {
        return progDailyService.addProgDaily(progDaily.toEntity());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
