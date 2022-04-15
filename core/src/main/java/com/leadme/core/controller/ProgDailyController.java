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
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProgDailyController {
    private final ProgDailyService progDailyService;
    private final ProgDailyRepository progDailyRepository;
    private final ProgRepository progRepository;

    @Transactional
    @PostMapping("/prog-dailies")
    public Long addProgDaily(@RequestBody ProgDailyDto progDailyDto) {
        return progDailyService.addProgDaily(progDailyDto.toEntity());
    }

    //TODO PathVariable
    @GetMapping("/prog-dailies")
    public Result findProgDaily(@RequestBody ProgDailyDto progDailyDto) {
        return new Result(progDailyRepository.findByProgAndProgDate(progDailyDto.getProg(), progDailyDto.getProgDate())
                .stream()
                .map(ProgDailyDto::new)
                .collect(Collectors.toList())
        );
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Transactional
    @DeleteMapping("/prog-dailies/{id}")
    public void deleteProgDaily(@PathVariable("id") Long progDailyId) {
        progDailyService.deleteProgDaily(progDailyId);
    }
}
