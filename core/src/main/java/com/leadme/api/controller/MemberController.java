package com.leadme.api.controller;

import com.leadme.api.dto.MemberDto;
import com.leadme.api.dto.condition.MemberSearchCondition;
import com.leadme.api.dto.sdto.MemberGuideDto;
import com.leadme.api.repository.member.MemberQueryRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

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

    @GetMapping("/member/{id}")
    public Result findMember(@PathVariable("id") Long memberId) {
        return new Result(memberRepository.findById(memberId)
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/members/{content}")
    public Page<MemberGuideDto> searchMembers(@PathVariable("content") String content, Pageable pageable) {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setName(content);
        condition.setEmail(content);
        return memberQueryRepository.searchMembers(condition, pageable);
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
