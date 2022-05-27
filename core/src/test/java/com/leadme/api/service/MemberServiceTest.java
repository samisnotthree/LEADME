package com.leadme.api.service;

import com.leadme.api.entity.Member;
import com.leadme.api.repository.member.MemberRepository;
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
            .inDate(LocalDateTime.now())
            .build();

        // when
        Long memberId = memberService.joinMember(member);

        //then
        Member foundMember = memberRepository.findById(memberId).get();
        assertThat(foundMember).isSameAs(member);
    }

    @Test
    @DisplayName("사용자 중복 등록")
    @Transactional
    void join_member_duplicate() {
        // given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName2")
            .pass("testPass2")
            .phone("testPhone2")
            .inDate(LocalDateTime.now())
            .build();
        memberService.joinMember(member);

        // when
        Throwable exception = catchThrowable(() -> {
            memberService.joinMember(member);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("사용자 등록 null 이메일")
    @Transactional
    void join_member_null_email() {
        // given
        Member member = Member.builder()
                .email(null)
                .name("testName")
                .pass("testPass")
                .phone("testPhone")
                .inDate(LocalDateTime.now())
                .build();

        // when
        Throwable exception = catchThrowable(() -> {
            memberService.joinMember(member);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("이메일은 필수 입력 값입니다.");
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
            .inDate(LocalDateTime.now())
            .build();
        
        Long joinedMemberId = memberService.joinMember(member);

        //when
        memberService.deleteMember(joinedMemberId);

        //then
        assertThat(memberRepository.findById(joinedMemberId).get().getOutDate()).isNotNull();
    }

    @Test
    @DisplayName("사용자 삭제 null 사용자")
    @Transactional
    void delete_member_null() {
        //when
        Throwable exception = catchThrowable(() -> {
            memberService.deleteMember(null);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("사용자 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("사용자 삭제 없는 사용자")
    @Transactional
    void delete_member_not_exists() {
        //when
        Throwable exception = catchThrowable(() -> {
            memberService.deleteMember(Long.MAX_VALUE);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 사용자입니다.");
    }
}
