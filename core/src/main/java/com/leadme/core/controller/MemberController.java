package com.leadme.core.controller;

import com.leadme.core.dto.MemberDto;
import com.leadme.core.repository.MemberRepository;
import com.leadme.core.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Transactional
    @PostMapping("/joinMember")
    public Long joinMember(@RequestBody MemberDto memberDto) {
        return memberService.joinMember(memberDto.toEntity());
    }

    @GetMapping("/findMember")
    public Result findAll() {
        return new Result(memberRepository.findAll()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList()));
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
