package com.leadme.api.controller.form;

import com.leadme.api.controller.MemberController;
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
import org.apache.tomcat.jni.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberFormController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    @GetMapping("/member/v2")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberDto());
        return "members/createMemberForm";
    }

    @GetMapping("/member/v2/mypage")
    public String mypageForm(Model model) {
        Optional<Member> member = memberRepository.findById(1L);
        member.ifPresent(m -> model.addAttribute("member", m));
        return "members/mypage";
    }

    @PostMapping("/members/v2")
    public String create(@Valid MemberDto memberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        memberService.joinMember(memberDto.toEntity());

        return "redirect:/";
    }

    @GetMapping("/members/v2")
    public String findAll(Model model) {
        List<MemberDto> members = memberRepository.findAll()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/member/v2/{id}")
    public Result findMember(@PathVariable("id") Long memberId) {
        return new Result(memberRepository.findById(memberId)
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/members/v2/{content}")
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
    @PatchMapping("/members/v2")
    public String changeMember(MemberDto memberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "members/mypage";
        }
        Optional<Member> member = memberRepository.findById(memberDto.getMemberId());
        member.ifPresent(m -> m.changeMemberInfo(memberDto.toEntity()));
        return "redirect:/";
    }

    @Transactional
    @DeleteMapping("/members/v2/{id}")
    public void deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
    }

}
