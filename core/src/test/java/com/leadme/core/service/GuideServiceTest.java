package com.leadme.core.service;

import com.leadme.core.entity.Guide;
import com.leadme.core.entity.Member;
import com.leadme.core.repository.GuideRepository;
import com.leadme.core.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuideServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired GuideRepository guideRepository;
    @Autowired GuideService guideService;

    @Test
    @DisplayName("가이드 등록 테스트")
    @Transactional
    void joinGuide() {
        // given
        Member member = new Member();
        member.setName("testName");
        member.setEmail("testEmail");

        Member savedMember = memberRepository.save(member);

        String desc = "testDesc";

        // when
        Long joinedGuideId = guideService.joinGuide(savedMember.getMemberId(), desc);
        Guide foundGuide = guideRepository.findById(joinedGuideId).get();

        // then
        assertThat(foundGuide.getMember()).isSameAs(savedMember);
        assertThat(foundGuide.getDesc()).isEqualTo(desc);
    }

    @Test
    void deleteGuide() {
        // given
        Member member = new Member();
        member.setName("testName");
        member.setEmail("testEmail");

        Member savedMember = memberRepository.save(member);

        String desc = "testDesc";

        // when
        Long joinedGuideId = guideService.joinGuide(savedMember.getMemberId(), desc);
        guideService.deleteGuide(joinedGuideId);

        Guide foundGuide = guideRepository.findById(joinedGuideId).get();

        //then
        assertThat(foundGuide.getOutDate()).isNotNull();
    }
}