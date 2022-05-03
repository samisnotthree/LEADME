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
    void join_member() {
        // given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName")
            .pass("testPass")
            .phone("testPhone")
            .photo("testPhoto")
            .inDate(LocalDateTime.now())
            .outDate(null)
            .guide(null)
            .build();

        // when
        Long memberId = memberService.joinMember(member);

        //then
        Member foundMember = memberRepository.findById(memberId).get();
        assertThat(foundMember).isSameAs(member);
    }

    @Test
    @DisplayName("사용자 삭제")
    @Transactional
    void delete_member() {
        //given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName")
            .pass("testPass")
            .phone("testPhone")
            .photo("testPhoto")
            .inDate(LocalDateTime.now())
            .outDate(null)
            .guide(null)
            .build();
        
        Long joinedMemberId = memberService.joinMember(member);

        //when
        memberService.deleteMember(joinedMemberId);

        //then
        assertThat(memberRepository.findById(joinedMemberId).get().getOutDate()).isNotNull();
    }
}
