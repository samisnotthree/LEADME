package com.leadme.api.repository.guideHashtag.controller;

import com.leadme.api.dto.MemberDto;
import com.leadme.api.dto.condition.MemberSearchCondition;
import com.leadme.api.dto.sdto.MemberGuideDto;
import com.leadme.api.entity.Member;
import com.leadme.api.repository.member.MemberQueryRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
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
    @PatchMapping("/members")
    public void changeMember(@RequestBody MemberDto memberDto) {
        Optional<Member> member = memberRepository.findById(memberDto.getMemberId());
        member.ifPresent(m -> m.changeMemberInfo(memberDto.toEntity()));
    }

    @Transactional
    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
    }
}
