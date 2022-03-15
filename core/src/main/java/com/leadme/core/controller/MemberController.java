package com.leadme.core.controller;

import com.leadme.core.dto.MemberDto;
import com.leadme.core.repository.MemberRepository;
import com.leadme.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

//    public List<MemberDto> findAll() {
//        return memberRepository.findAll()
//                .stream()
//                .map(MemberDto::new)
//                .collect(Collectors.toList());
//    }
}
