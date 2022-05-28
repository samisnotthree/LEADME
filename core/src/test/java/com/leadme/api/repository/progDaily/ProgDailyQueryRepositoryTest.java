package com.leadme.api.repository.progDaily;

import com.leadme.api.dto.condition.ProgDailySearchCondition;
import com.leadme.api.dto.sdto.ProgDailyProgDto;
import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgDaily;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.service.ProgDailyService;
import com.leadme.api.service.ProgService;
import com.leadme.dummy.GuideDummy;
import com.leadme.dummy.MemberDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class ProgDailyQueryRepositoryTest {
    @Autowired ProgDailyQueryRepository progDailyQueryRepository;
    @Autowired
    ProgDailyService progDailyService;
    @Autowired
    ProgService progService;
    @Autowired
    ProgRepository progRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuideRepository guideRepository;

    @BeforeEach
    void init_data() {
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = Prog.builder()
                .name("name11")
                .desc("desc11")
                .guide(guide)
                .build();
        progService.addProg(prog);

        ProgDaily progDaily = ProgDaily.builder()
                .progDate("202205151500")
                .prog(prog)
                .build();
        progDailyService.addProgDaily(progDaily);

        ProgDaily progDaily2 = ProgDaily.builder()
                .progDate("202205151630")
                .prog(prog)
                .build();
        progDailyService.addProgDaily(progDaily2);
    }

    @Test
    @DisplayName("특정 날짜 일정 조회")
    @Transactional
    void search_schedules() {
        //given
        Prog prog = progRepository.findAll().get(0);

        ProgDailySearchCondition condition = new ProgDailySearchCondition();
        condition.setProgId(prog.getProgId());
        condition.setProgDate("20220515");

        //when
        List<ProgDailyProgDto> schedules = progDailyQueryRepository.findSchedules(condition);

        //then
        assertThat(schedules).extracting("progDate").containsExactly("202205151500", "202205151630");
    }

    @Test
    @DisplayName("특정 날짜 일정 조회 null 프로그램")
    @Transactional
    void search_schedules_null_prog() {
        //given
        Prog prog = progRepository.findAll().get(0);

        ProgDailySearchCondition condition = new ProgDailySearchCondition();
        condition.setProgId(null);
        condition.setProgDate("20220515");

        //when
        Throwable exception = catchThrowable(() -> {
            progDailyQueryRepository.findSchedules(condition);
        });

        //then
        //assertThat(exception).isInstanceOf(IllegalStateException.class);
        //assertThat(exception).hasMessage("프로그램 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("특정 날짜 일정 조회 null 프로그램 일정")
    @Transactional
    void search_schedules_null_progDate() {
        //given
        Prog prog = progRepository.findAll().get(0);

        ProgDailySearchCondition condition = new ProgDailySearchCondition();
        condition.setProgId(prog.getProgId());
        condition.setProgDate(null);

        //when
        Throwable exception = catchThrowable(() -> {
            progDailyQueryRepository.findSchedules(condition);
        });

        //then
        //assertThat(exception).isInstanceOf(IllegalStateException.class);
        //assertThat(exception).hasMessage("프로그램 일정이 올바르지 않습니다.");
    }
}
