package com.leadme.api.controller;

import com.leadme.api.dto.ProgDto;
import com.leadme.api.dto.ProgHashtagDto;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.service.ProgHashtagService;
import com.leadme.api.service.ProgService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProgController {
    private final ProgService progService;
    private final ProgRepository progRepository;
    private final ProgHashtagService progHashtagService;

    /**
     *  프로그램 등록
     */
    @Transactional
    @PostMapping("/progs")
    public Long addProg(@RequestBody ProgDto progDto) {
        return progService.addProg(progDto.toEntity());
    }

    /**
     *  프로그램 조회
     */
    @GetMapping("/progs/{desc}")
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

    /**
     *  프로그램 해시태그 등록
     */
    @Transactional
    @PostMapping("/progHashtags")
    public Long addProgHashtag(@RequestBody ProgHashtagDto progHashtagDto) {
        return progHashtagService.addProgHashtag(progHashtagDto.getProg().getProgId(), progHashtagDto.getHashtag().getHashtagId()).getProgHashtagId();
    }
}
