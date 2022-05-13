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

@SpringBootTest
class GuideServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired GuideRepository guideRepository;
    @Autowired GuideService guideService;

    @Test
    @DisplayName("가이드 등록")
    @Transactional
    void join_guide() {
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

        Member savedMember = memberRepository.save(member);

        String desc = "testDesc";

        // when
        Long joinedGuideId = guideService.joinGuide(savedMember.getMemberId(), desc).getGuideId();

        // then
        Guide foundGuide = guideRepository.findById(joinedGuideId).get();
        assertThat(foundGuide.getMember()).isSameAs(savedMember);
        assertThat(foundGuide.getDesc()).isEqualTo(desc);
    }

    @Test
    @DisplayName("가이드 삭제")
    @Transactional
    void delete_guide() {
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

        Member savedMember = memberRepository.save(member);
        String desc = "testDesc";
        Long joinedGuideId = guideService.joinGuide(savedMember.getMemberId(), desc).getGuideId();

        // when
        guideService.deleteGuide(joinedGuideId);

        //then
        Guide foundGuide = guideRepository.findById(joinedGuideId).get();
        assertThat(foundGuide.getOutDate()).isNotNull();
    }
}