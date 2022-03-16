package com.leadme.core.service;

import com.leadme.core.entity.Prog;
import com.leadme.core.entity.ProgDaily;
import com.leadme.core.repository.ProgDailyRepository;
import com.leadme.core.repository.ProgRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    void addProgDaily() {
        //given
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
        ProgDaily foundProgDaily = progDailyRepository.findById(addedProgDailyId).get();

        //then
        assertThat(foundProgDaily).isSameAs(progDaily);
    }

    @Test
    @DisplayName("일정 일시 중복 체크")
    @Transactional
    void addProgDailyDuplicate() {
        //given
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
            .guide(null)
            .build();
        
        Long addedProgId = progService.addProg(prog);
        Prog foundProg = progRepository.findById(addedProgId).get();

        LocalDateTime now = LocalDateTime.now();
        ProgDaily progDaily = ProgDaily.builder()
            .progDate(now)
            .prog(foundProg)
            .build();

        //when
        Long addedProgDailyId = progDailyService.addProgDaily(progDaily);

        //then
        Throwable exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            progDailyService.addProgDaily(progDaily);
        });
        Assertions.assertEquals("해당 일시에 이미 등록되어 있습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("일정 삭제")
    @Transactional
    void deleteProgDaily() {
        //given
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
        progDailyService.deleteProgDaily(addedProgDailyId);

        Optional<ProgDaily> foundProgDaily = progDailyRepository.findById(addedProgDailyId);

        //then
        assertThat(foundProgDaily).isInstanceOf(Optional.class).isNotPresent();
    }

}
