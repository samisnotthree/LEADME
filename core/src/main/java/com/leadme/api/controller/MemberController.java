package com.leadme.api.controller;

import com.leadme.api.dto.MemberDto;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.service.MemberService;
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
    @PostMapping("/members")
    public Long joinMember(@RequestBody MemberDto memberDto) {
        return memberService.joinMember(memberDto.toEntity());
    }

    @GetMapping("/members")
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
    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
    }
}
