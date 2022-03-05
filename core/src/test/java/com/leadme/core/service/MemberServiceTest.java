package com.leadme.core.service;

import com.leadme.core.entity.Member;
import com.leadme.core.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
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
        Assertions.assertThat(foundMember.getName()).isEqualTo("testName");
    }


    @Test
    void deleteMember() {
        //given
        Member member = new Member();
        Long joinedMemberId = memberService.join(member);

        //when
        memberService.deleteMember(memberRepository.findById(joinedMemberId).get());

        //then
        Assertions.assertThat(memberRepository.findById(joinedMemberId).get().getOutDate()).isNotNull();
    }
}