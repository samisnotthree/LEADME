package com.leadme.core.service;

import com.leadme.core.entity.Guide;
import com.leadme.core.entity.Member;
import com.leadme.core.entity.Prog;
import com.leadme.core.repository.ProgRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProgServiceTest {

    @Autowired ProgService progService;
    @Autowired ProgRepository progRepository;
    @Autowired GuideService guideService;
    @Autowired MemberService memberService;

    @Test
    @DisplayName("프로그램 추가")
    @Transactional
    void addTest() {
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

        Long joinedMember = memberService.joinMember(member);

        Guide joinedGuide = guideService.joinGuide(joinedMember, "testGuideDesc");

        Prog prog = Prog.builder()
            .name("testName")
            .desc("testDesc")
            .maxMember(5)
            .duration("두세시간")
            .price(50000L)
            .prepMat("준비물")
            .meetLocation("정문앞")
            .inDate(LocalDateTime.now())
            .outDate(LocalDateTime.now())
            .guide(joinedGuide)
            .build();

        //when
        Long addedProg = progService.addProg(prog);
        Prog foundProg = progRepository.findById(addedProg).get();

        //then
        assertThat("testProg").isEqualTo(foundProg.getName());
        assertThat(joinedGuide).isSameAs(foundProg.getGuide());
    }

    @Test
    @DisplayName("프로그램 삭제")
    @Transactional
    void deleteTest() {
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

        Long joinedMember = memberService.joinMember(member);

        Guide joinedGuide = guideService.joinGuide(joinedMember, "testGuideDesc");

        Prog prog = Prog.builder()
            .name("testName")
            .desc("testDesc")
            .maxMember(5)
            .duration("두세시간")
            .price(50000L)
            .prepMat("준비물")
            .meetLocation("정문앞")
            .inDate(LocalDateTime.now())
            .outDate(LocalDateTime.now())
            .guide(joinedGuide)
            .build();

        //when
        Long addedProg = progService.addProg(prog);
        progService.deleteProg(addedProg);
        Prog foundProg = progRepository.findById(addedProg).get();

        //then
        assertThat(foundProg.getOutDate()).isNotNull();
    }
}
