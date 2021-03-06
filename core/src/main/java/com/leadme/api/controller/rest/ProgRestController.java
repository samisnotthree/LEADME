package com.leadme.api.controller.rest;

import com.leadme.api.dto.ProgDto;
import com.leadme.api.dto.ProgHashtagDto;
import com.leadme.api.dto.condition.ProgSearchCondition;
import com.leadme.api.entity.Prog;
import com.leadme.api.repository.prog.ProgQueryRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.service.ProgHashtagService;
import com.leadme.api.service.ProgService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProgRestController {
    private final ProgService progService;
    private final ProgRepository progRepository;
    private final ProgHashtagService progHashtagService;
    private final ProgQueryRepository progQueryRepository;

    /**
     *  프로그램 등록
     */
    @Transactional
    @PostMapping("/progs")
    public ResponseEntity<Long> addProg(@RequestBody ProgDto progDto) {
        return ResponseEntity.ok(progService.addProg(progDto.toEntity()));
    }

    /**
     *  프로그램 상세 조회
     */
    @GetMapping("/prog/{id}")
    public Result searchProg(@PathVariable("id") Long progId) {
        return new Result(progRepository.findById(progId)
                .stream()
                .map(ProgDto::new)
                .collect(Collectors.toList()));
    }

    /**
     *  프로그램 조회
     */
    @GetMapping("/progs/{content}")
    public Result searchProgs(@PathVariable("content") String content, Pageable pageable) {
        ProgSearchCondition condition = new ProgSearchCondition();
        condition.setName(content);
        condition.setDesc(content);
        return new Result(progQueryRepository.searchProgs(condition, pageable));
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Transactional
    @PatchMapping("/progs")
    public void changeProg(@RequestBody ProgDto progDto) {
        Optional<Prog> prog = progRepository.findById(progDto.getProgId());
        prog.ifPresent(p -> p.changeProgInfo(progDto.toEntity()));
    }

    /**
     *  프로그램 해시태그 등록
     */
    @Transactional
    @PostMapping("/prog-hashtags")
    public ResponseEntity<Long> addProgHashtag(@RequestBody ProgHashtagDto progHashtagDto) {
        return ResponseEntity.ok(progHashtagService.addProgHashtag(progHashtagDto.getProg().getProgId(), progHashtagDto.getHashtag().getHashtagId()).getProgHashtagId());
    }
}
