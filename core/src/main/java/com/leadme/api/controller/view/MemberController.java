package com.leadme.api.controller.view;

import com.leadme.api.dto.GuideDto;
import com.leadme.api.dto.MemberDto;
import com.leadme.api.dto.condition.MemberSearchCondition;
import com.leadme.api.dto.condition.OrderSearchCondition;
import com.leadme.api.dto.sdto.MemberGuideDto;
import com.leadme.api.dto.sdto.OrderProgDailyDto;
import com.leadme.api.entity.Member;
import com.leadme.api.repository.member.MemberQueryRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.order.OrderQueryRepository;
import com.leadme.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/member/v2")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberDto());
        return "members/createMemberForm";
    }

    @GetMapping("/member/v2/mypage")
    public String mypageForm(Model model) {
        Long memberId = 1L;
        Optional<Member> member = memberRepository.findById(memberId);
        member.ifPresent(m -> model.addAttribute("member", m));
        model.addAttribute("memberForm", new MemberDto());
        model.addAttribute("guideForm", new GuideDto());

        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setMemberId(memberId);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("orderId").descending());

        List<OrderProgDailyDto> orders = orderQueryRepository.searchOrdersByMemberId(condition, pageable).getContent();
        model.addAttribute("orders", orders);

        return "members/mypage";
    }

    @Transactional
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
    public String findMember(@PathVariable("id") Long memberId, Model model) {
        List<MemberDto> members = memberRepository.findById(memberId)
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/members/v2/{content}")
    public String searchMembers(@PathVariable("content") String content, Pageable pageable, Model model) {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setName(content);
        condition.setEmail(content);
        Page<MemberGuideDto> members = memberQueryRepository.searchMembers(condition, pageable);
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @Transactional
    @PostMapping("/members/v2/update")
    public String changeMember(MemberDto memberDto, BindingResult result) {
        memberDto.setMemberId(1L);
        if (result.hasErrors()) {
            return "members/mypage";
        }
        Optional<Member> member = memberRepository.findById(memberDto.getMemberId());
        member.ifPresent(m -> m.changeMemberInfo(memberDto.toEntity()));
        return "redirect:/";
    }

    @Transactional
    @GetMapping("/members/v2/delete/{id}")
    public String deleteMember(@PathVariable("id") Long memberId) {
        memberRepository.deleteById(memberId);
        return "redirect:/";
    }

}
