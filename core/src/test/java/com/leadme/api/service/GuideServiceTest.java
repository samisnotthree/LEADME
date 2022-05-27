package com.leadme.api.service;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class GuideServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired GuideRepository guideRepository;
    @Autowired GuideService guideService;

    @Test
    @DisplayName("가이드 등록")
    @Transactional
    void join_guide() {
        //given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName")
            .pass("testPass")
            .phone("testPhone")
            .inDate(LocalDateTime.now())
            .build();

        Member savedMember = memberRepository.save(member);

        String desc = "testDesc";

        //when
        Long joinedGuideId = guideService.joinGuide(savedMember.getMemberId(), desc).getGuideId();

        //then
        Guide foundGuide = guideRepository.findById(joinedGuideId).get();
        assertThat(foundGuide.getMember()).isSameAs(savedMember);
        assertThat(foundGuide.getDesc()).isSameAs(desc);
    }

    @Test
    @DisplayName("가이드 등록 중복")
    @Transactional
    void join_guide_duplicate() {
        //given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName")
            .pass("testPass")
            .phone("testPhone")
            .inDate(LocalDateTime.now())
            .build();

        Member savedMember = memberRepository.save(member);

        String desc = "testDesc";
        Long joinedGuideId = guideService.joinGuide(savedMember.getMemberId(), desc).getGuideId();

        //when
        Throwable exception = catchThrowable(() -> {
            guideService.joinGuide(savedMember.getMemberId(), desc).getGuideId();
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("이미 가이드로 등록되어 있습니다.");
    }

    @Test
    @DisplayName("가이드 등록 null 사용자")
    @Transactional
    void join_guide_null_member() {
        //when
        Throwable exception = catchThrowable(() -> {
            guideService.joinGuide(null, "testDesc");
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("사용자 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("가이드 등록 없는 사용자")
    @Transactional
    void join_guide_not_exists_member() {
        //when
        Throwable exception = catchThrowable(() -> {
            guideService.joinGuide(Long.MAX_VALUE, "testDesc");
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("가이드 삭제")
    @Transactional
    void delete_guide() {
        //given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName")
            .pass("testPass")
            .phone("testPhone")
            .inDate(LocalDateTime.now())
            .build();

        Member savedMember = memberRepository.save(member);
        String desc = "testDesc";
        Long joinedGuideId = guideService.joinGuide(savedMember.getMemberId(), desc).getGuideId();

        //when
        guideService.deleteGuide(joinedGuideId);

        //then
        Guide foundGuide = guideRepository.findById(joinedGuideId).get();
        assertThat(foundGuide.getOutDate()).isNotNull();
    }

    @Test
    @DisplayName("가이드 삭제 null 가이드")
    @Transactional
    void delete_guide_null() {
        //when
        Throwable exception = catchThrowable(() -> {
            guideService.deleteGuide(null);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("가이드 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("가이드 삭제 없는 가이드")
    @Transactional
    void delete_guide_not_exists() {
        //when
        Throwable exception = catchThrowable(() -> {
            guideService.deleteGuide(Long.MAX_VALUE);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 가이드입니다.");
    }
}
