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
        Member member = new Member();
        member.setName("testMemberName");
        Long joinedMember = memberService.joinMember(member);

        Guide joinedGuide = guideService.joinGuide(joinedMember, "testGuideDesc");

        Prog prog = new Prog();
        prog.setName("testProg");
        prog.setGuide(joinedGuide);

        //when
        Long addedProg = progService.addProg(prog);
        Prog foundProg = progRepository.findById(addedProg).get();

        //then
        assertThat("testProg").isEqualTo(foundProg.getName());
        assertThat(joinedGuide).isSameAs(foundProg.getGuide());
    }


}