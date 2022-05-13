package com.leadme.api.repository.prog;

import com.leadme.api.dto.ProgDto;
import com.leadme.api.dto.ProgSearchCondition;
import com.leadme.api.entity.Prog;
import com.leadme.api.service.ProgService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProgQueryRepositoryTest {
    @Autowired ProgQueryRepository progQueryRepository;
    @Autowired
    ProgService progService;

    @BeforeEach
    void init_data() {
        Prog prog = Prog.builder()
                .name("name11")
                .desc("desc11")
                .build();
        progService.addProg(prog);

        Prog prog2 = Prog.builder()
                .name("name22")
                .desc("desc22")
                .build();
        progService.addProg(prog2);
    }

    @Test
    @DisplayName("프로그램 전체 조회")
    @Transactional
    void search_progs() {
        //given
        ProgSearchCondition condition = new ProgSearchCondition();

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "name");

        //when
        Page<ProgDto> progs = progQueryRepository.searchProgs(condition, pageable);

        //then
        assertThat(progs.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("프로그램 이름으로 조회")
    @Transactional
    void search_progs_name() {
        //given
        ProgSearchCondition condition = new ProgSearchCondition();
        condition.setName("ame1");

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "name");

        //when
        Page<ProgDto> progs = progQueryRepository.searchProgs(condition, pageable);

        //then
        assertThat(progs.getContent().get(0).getName()).isEqualTo("name11");
    }

    @Test
    @DisplayName("프로그램 내용으로 조회")
    @Transactional
    void search_progs_desc() {
        //given
        ProgSearchCondition condition = new ProgSearchCondition();
        condition.setDesc("esc1");

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "name");

        //when
        Page<ProgDto> progs = progQueryRepository.searchProgs(condition, pageable);

        //then
        assertThat(progs.getContent().get(0).getDesc()).isEqualTo("desc11");
    }
}