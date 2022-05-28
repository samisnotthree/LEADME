package com.leadme.api.service;

import com.leadme.api.entity.*;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.order.OrderRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import com.leadme.dummy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class ProgDailyServiceTest {

    @Autowired ProgDailyService progDailyService;
    @Autowired ProgDailyRepository progDailyRepository;
    @Autowired ProgService progService;
    @Autowired ProgRepository progRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuideRepository guideRepository;
    @Autowired
    OrderRepository orderRepository;


    @Test
    @DisplayName("일정 등록")
    @Transactional
    void add_progDaily() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);

        //when
        Long addedProgDailyId = progDailyService.addProgDaily(progDaily);

        //then
        ProgDaily foundProgDaily = progDailyRepository.findById(addedProgDailyId).get();
        assertThat(foundProgDaily).isSameAs(progDaily);
    }

    @Test
    @DisplayName("일정 등록 null 프로그램")
    @Transactional
    void add_progDaily_null_prog() {
        //given
        ProgDaily progDaily = ProgDaily.builder()
            .progDate("202203161800")
            .build();

        //when
        Throwable exception = catchThrowable(() -> {
            progDailyService.addProgDaily(progDaily);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("프로그램 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("일정 등록 없는 프로그램")
    @Transactional
    void add_progDaily_not_exists_prog() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);

        progRepository.delete(prog);

        //when
        Throwable exception = catchThrowable(() -> {
            progDailyService.addProgDaily(progDaily);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 프로그램입니다.");
    }

    @Test
    @DisplayName("일정 등록 없는 프로그램")
    @Transactional
    void add_progDaily_null_progDate() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily(null, prog);

        //when
        Throwable exception = catchThrowable(() -> {
            progDailyService.addProgDaily(progDaily);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("프로그램 일정이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("일정 일시 중복 체크")
    @Transactional
    void add_progDaily_duplicate_exception() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyService.addProgDaily(progDaily);

        //when
        Throwable exception = catchThrowable(() -> {
            progDailyService.addProgDaily(progDaily);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("해당 일시에 이미 등록되어 있습니다.");
    }

    @Test
    @DisplayName("일정 삭제")
    @Transactional
    void delete_progDaily() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        //when
        progDailyService.deleteProgDaily(progDaily.getProgDailyId());

        //then
        Optional<ProgDaily> foundProgDaily = progDailyRepository.findById(progDaily.getProgDailyId());
        assertThat(foundProgDaily).isInstanceOf(Optional.class).isNotPresent();
    }

    @Test
    @DisplayName("일정 삭제 null 일정")
    @Transactional
    void delete_progDaily_null() {
        //when
        Throwable exception = catchThrowable(() -> {
            progDailyService.deleteProgDaily(null);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("프로그램 일정 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("일정 삭제 없는 일정")
    @Transactional
    void delete_progDaily_not_exists() {
        //when
        Throwable exception = catchThrowable(() -> {
            progDailyService.deleteProgDaily(Long.MAX_VALUE);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 프로그램 일정입니다.");
    }

    @Test
    @DisplayName("일정 삭제 주문자 있는 경우")
    @Transactional
    void delete_progDaily_already_ordered() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderRepository.save(order);

        //when
        Throwable exception = catchThrowable(() -> {
            progDailyService.deleteProgDaily(progDaily.getProgDailyId());
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("구매자가 있어 삭제할 수 없습니다.");
    }


    @Test
    @DisplayName("해당날짜 일정 시간 목록")
    @Transactional
    void find_schedules() {
        //given
        Prog prog = Prog.builder()
                .name("testName")
                .desc("testDesc")
                .maxMember(5)
                .duration("두세시간")
                .price(50000L)
                .meetLocation("정문앞")
                .inDate(LocalDateTime.now())
                .outDate(LocalDateTime.now())
                .guide(null)
                .build();

        Long addedProgId = progService.addProg(prog);
        Prog foundProg = progRepository.findById(addedProgId).get();
        LocalDateTime now = LocalDateTime.now();

        ProgDaily progDaily = ProgDaily.builder()
                .progDate(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
                .prog(foundProg)
                .build();
        Long addedProgDailyId = progDailyService.addProgDaily(progDaily);

        //when
        //List<ProgDaily> schedules = progDailyRepository.findSchedules(addedProgDailyId, now.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        //then
        //sql 문제 -> 곧 사라질 sql
        //assertThat(schedules).extracting("progDate").containsExactly(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
    }
}
