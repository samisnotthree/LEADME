package com.leadme.core.controller;

import com.leadme.core.dto.ProgDto;
import com.leadme.core.repository.ProgRepository;
import com.leadme.core.service.ProgService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProgController {
    private final ProgService progService;
    private final ProgRepository progRepository;

    /**
     *  프로그램 등록
     */
    @Transactional
    @PostMapping("/prog")
    public Long addProg(@RequestBody ProgDto progDto) {
        return progService.addProg(progDto.toEntity());
    }

    /**
     *  프로그램 조회
     */
    @GetMapping("/prog/{desc}")
    public Result findProgs(@PathVariable("desc") String desc) {
        return new Result(progRepository.findByDescContaining(desc)
                .stream()
                .map(ProgDto::new)
                .collect(Collectors.toList()));
    }

    /**
     *  프로그램 상세 조회
     */
//    @PostMapping("/findProgDaily")
//    public Result findOneProg(Long progId) {
//        Optional<Prog> foundProg = progRepository.findById(progId);
//        foundProg.ifPresent(prog -> {
//            return new Result()
//        });
//    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
