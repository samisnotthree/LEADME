package com.leadme.api.controller.rest;

import com.leadme.api.dto.ProgDailyDto;
import com.leadme.api.dto.condition.ProgDailySearchCondition;
import com.leadme.api.entity.ProgDaily;
import com.leadme.api.repository.progDaily.ProgDailyQueryRepository;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import com.leadme.api.service.ProgDailyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProgDailyRestController {
    private final ProgDailyService progDailyService;
    private final ProgDailyRepository progDailyRepository;
    private final ProgDailyQueryRepository progDailyQueryRepository;

    @Transactional
    @PostMapping("/prog-dailies")
    public Long addProgDaily(@RequestBody ProgDailyDto progDailyDto) {
        return progDailyService.addProgDaily(progDailyDto.toEntity());
    }

    @GetMapping("/prog-dailies/{progId}/{progDate}")
    public Result findProgDailies(@PathVariable("progId") Long progId, @PathVariable("progDate") String progDate) {
        ProgDailySearchCondition condition = new ProgDailySearchCondition();
        condition.setProgId(progId);
        condition.setProgDate(progDate);
        return new Result(progDailyQueryRepository.findSchedules(condition));
    }
    
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
    @PatchMapping("/prog-dailies/{progDate}")
    public void changeProgDate(Long progDailyId, @PathVariable("progDate") String progDate) {
        Optional<ProgDaily> progDaily = progDailyRepository.findById(progDailyId);
        progDaily.ifPresent(pd -> pd.changeProgDate(progDate));
    }

    @Transactional
    @DeleteMapping("/prog-dailies/{id}")
    public void deleteProgDaily(@PathVariable("id") Long progDailyId) {
        progDailyService.deleteProgDaily(progDailyId);
    }
}
