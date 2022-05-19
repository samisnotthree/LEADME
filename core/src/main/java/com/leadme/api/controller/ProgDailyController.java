package com.leadme.api.controller;

import com.leadme.api.dto.ProgDailyDto;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import com.leadme.api.service.ProgDailyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProgDailyController {
    private final ProgDailyService progDailyService;
    private final ProgDailyRepository progDailyRepository;

    @Transactional
    @PostMapping("/prog-dailies")
    public Long addProgDaily(@RequestBody ProgDailyDto progDailyDto) {
        return progDailyService.addProgDaily(progDailyDto.toEntity());
    }

//    @GetMapping("/prog-dailies")
//    public Result findProgDailies(@PathVariable("progId") Long progId, @PathVariable("progDate") String progDate) {
//        return new Result(progDailyRepository.findSchedules(progId, progDate)
//                .stream()
//                .map(ProgDailyDto::new)
//                .collect(Collectors.toList())
//        );
//    }
    
//     @GetMapping("/prog-dailies")
//     public Result findProgDaily(@PathVariable("id") Long progDailyId) {
//         ProgDaily progDaily = progDailyRepository.findById(progDailyId).get();
//         return new Result(progDailyRepository.findByProgAndProgDate(progDaily.getProg(), progDaily.getProgDate())
//                 .stream()
//                 .map(ProgDailyDto::new)
//                 .collect(Collectors.toList())
//         );
//     }

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
