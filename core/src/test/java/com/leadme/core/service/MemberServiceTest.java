package com.leadme.core.service;

import com.leadme.core.entity.Member;
import com.leadme.core.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("사용자 등록")
    void join() {
        // case
        Member member = new Member();

        member.setEmail("test@test.com");
        member.setPass("testPass");
        member.setName("testName");
        member.setInDate(LocalDateTime.now());

        // when
        Long memberId = memberService.join(member);
        Member foundMember = memberRepository.findById(memberId).get();

        //then
        assertThat(foundMember.getName()).isEqualTo("testName");
    }

    @Test
    @DisplayName("사용자 삭제")
    void deleteMember() {
        //given
        Member member = new Member();
        Long joinedMemberId = memberService.join(member);

        //when
        memberService.deleteMember(joinedMemberId);

        //then
        assertThat(memberRepository.findById(joinedMemberId).get().getOutDate()).isNotNull();
    }
}
