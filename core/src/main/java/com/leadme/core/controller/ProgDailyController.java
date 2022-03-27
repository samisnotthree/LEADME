package com.leadme.core.controller;

import com.leadme.core.dto.ProgDailyDto;
import com.leadme.core.entity.ProgDaily;
import com.leadme.core.repository.ProgDailyRepository;
import com.leadme.core.repository.ProgRepository;
import com.leadme.core.service.ProgDailyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public void deleteProgDaily(@RequestBody ProgDailyDto progDailyDto) {
        progDailyService.deleteProgDaily(progDailyDto.getProgDailyId());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
