package com.leadme.core.controller;

import com.leadme.core.dto.MemberDto;
import com.leadme.core.repository.MemberRepository;
import com.leadme.core.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Transactional
    @PostMapping("/member")
    public Long joinMember(@RequestBody MemberDto memberDto) {
        return memberService.joinMember(memberDto.toEntity());
    }

    @GetMapping("/member")
    public Result findAll() {
        return new Result(memberRepository.findAll()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList()));
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T members;
    }
    
    @Transactional
    @DeleteMapping("/member/{id}")
    public void deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
    }
}
