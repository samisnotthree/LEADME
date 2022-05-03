package com.leadme.api.service;

import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgDaily;
import com.leadme.api.repository.ProgDailyRepository;
import com.leadme.api.repository.ProgRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProgDailyServiceTest {

    @Autowired ProgDailyService progDailyService;
    @Autowired ProgDailyRepository progDailyRepository;
    @Autowired ProgService progService;
    @Autowired ProgRepository progRepository;

    @Test
    @DisplayName("일정 등록")
    @Transactional
    void add_progDaily() {
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

        ProgDaily progDaily = ProgDaily.builder()
            .progDate(LocalDateTime.now())
            .prog(foundProg)
            .build();
        
        //when
        Long addedProgDailyId = progDailyService.addProgDaily(progDaily);

        //then
        ProgDaily foundProgDaily = progDailyRepository.findById(addedProgDailyId).get();
        assertThat(foundProgDaily).isSameAs(progDaily);
    }

    @Test
    @DisplayName("일정 일시 중복 체크")
    @Transactional
    void add_progDaily_duplicate_exception() {
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

        ProgDaily progDaily = ProgDaily.builder()
            .progDate(LocalDateTime.of(2022, 3, 16, 18, 0))
            .prog(foundProg)
            .build();
        Long addedProgDailyId = progDailyService.addProgDaily(progDaily);

        //when
        Throwable exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            progDailyService.addProgDaily(progDaily);
        });

        //then
        Assertions.assertEquals("해당 일시에 이미 등록되어 있습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("일정 삭제")
    @Transactional
    void delete_progDaily() {
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

        ProgDaily progDaily = ProgDaily.builder()
            .progDate(LocalDateTime.now())
            .prog(foundProg)
            .build();
        Long addedProgDailyId = progDailyService.addProgDaily(progDaily);

        //when
        progDailyService.deleteProgDaily(addedProgDailyId);

        //then
        Optional<ProgDaily> foundProgDaily = progDailyRepository.findById(addedProgDailyId);
        assertThat(foundProgDaily).isInstanceOf(Optional.class).isNotPresent();
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
                .progDate(now)
                .prog(foundProg)
                .build();
        Long addedProgDailyId = progDailyService.addProgDaily(progDaily);

        //when
        List<ProgDaily> schedules = progDailyRepository.findSchedules(addedProgDailyId, now.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        //then
        assertThat(schedules).isNotNull();
    }
}
