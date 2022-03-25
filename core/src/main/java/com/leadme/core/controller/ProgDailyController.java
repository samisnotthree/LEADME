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
    @PostMapping("/progDaily")
    public Long addProgDaily(@RequestBody ProgDailyDto progDailyDto) {
        return progDailyService.addProgDaily(progDailyDto.toEntity());
    }
    
    @Transactional
    @DeleteMapping("progDaily")
    public void deleteProgDaily(@RequestBody ProgDaily progDily) {
        progDailyService.deleteProgDaily(progDailyDto.toEntity());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
