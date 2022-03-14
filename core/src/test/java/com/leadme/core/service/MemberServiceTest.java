package com.leadme.core.service;

import com.leadme.core.dto.MemberDto;
import com.leadme.core.entity.Member;
import com.leadme.core.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("사용자 등록")
    @Transactional
    void join() {
        // case
        Member member = new Member("test@test.com", "testName", "testPass", "testPhone", "testPhoto", LocalDateTime.now(),null,null);

        // when
        Long memberId = memberService.joinMember(member);
        Member foundMember = memberRepository.findById(memberId).get();

        //then
        assertThat(foundMember).isSameAs(member);
    }

    @Test
    @DisplayName("사용자 삭제")
    @Transactional
    void deleteMember() {
        //given
        Member member = new Member("test@test.com", "testName", "testPass", "testPhone", "testPhoto", LocalDateTime.now(),null,null);
        Long joinedMemberId = memberService.joinMember(member);

        //when
        memberService.deleteMember(joinedMemberId);

        //then
        assertThat(memberRepository.findById(joinedMemberId).get().getOutDate()).isNotNull();
    }
}
