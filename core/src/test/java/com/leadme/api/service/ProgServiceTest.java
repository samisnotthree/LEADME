package com.leadme.api.service;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import com.leadme.api.entity.Prog;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.dummy.GuideDummy;
import com.leadme.dummy.MemberDummy;
import com.leadme.dummy.ProgDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class ProgServiceTest {

    @Autowired ProgService progService;
    @Autowired ProgRepository progRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuideRepository guideRepository;


    @Test
    @DisplayName("프로그램 추가")
    @Transactional
    void add_prog() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3

        //when
        Long addedProg = progService.addProg(prog);

        //then
        Prog foundProg = progRepository.findById(addedProg).get();
        assertThat(prog).isSameAs(foundProg);
    }

    @Test
    @DisplayName("프로그램 추가 null 가이드")
    @Transactional
    void add_prog_null_guide() {
        //given
        Prog prog = ProgDummy.createProg(1, null); //max:3

        //when
        Throwable exception = catchThrowable(() -> {
            progService.addProg(prog);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("가이드 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("프로그램 추가 없는 가이드")
    @Transactional
    void add_prog_not_exists_guide() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);
        guideRepository.delete(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3

        //when
        Throwable exception = catchThrowable(() -> {
            progService.addProg(prog);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 가이드입니다.");
    }

    @Test
    @DisplayName("프로그램 추가 null name")
    @Transactional
    void add_prog_null_name() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = Prog.builder()
                .name(null)
                .desc("경복궁과 광화문광장을 주제로 투어합니다.")
                .maxMember(2)
                .duration("대략 한시간 반")
                .price(20000L)
                .meetLocation("광화문 앞")
                .guide(guide)
                .build();

        //when
        Throwable exception = catchThrowable(() -> {
            progService.addProg(prog);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("프로그램명이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("프로그램 추가 중복 프로그램명")
    @Transactional
    void add_prog_exists_name() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3
        Prog prog2 = ProgDummy.createProg(1, guide);
        progService.addProg(prog);

        //when
        Throwable exception = catchThrowable(() -> {
            progService.addProg(prog2);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("이미 존재하는 프로그램명입니다.");
    }


    @Test
    @DisplayName("프로그램 삭제")
    @Transactional
    void delete_prog() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3
        progRepository.save(prog);

        //when
        progService.deleteProg(prog.getProgId());

        //then
        Prog foundProg = progRepository.findById(prog.getProgId()).get();
        assertThat(foundProg.getOutDate()).isNotNull();
    }
}
