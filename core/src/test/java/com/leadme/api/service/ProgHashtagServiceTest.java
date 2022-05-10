package com.leadme.api.service;

import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgHashtag;
import com.leadme.api.entity.Hashtag;
import com.leadme.api.repository.progHashtag.ProgHashtagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class ProgHashtagServiceTest {
    @Autowired ProgHashtagService progHashtagService;
    @Autowired ProgHashtagRepository progHashtagRepository;
    @Autowired ProgService progService;
    @Autowired HashtagService hashtagService;

    @Test
    @DisplayName("프로그램_해시태그 등록")
    @Transactional
    void add_progHashtag() {
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

        Hashtag hashtag = Hashtag.builder()
                .name("서울")
                .build();
        Long addedHashtagId = hashtagService.addHashtag(hashtag);

        //when
        ProgHashtag progHashtag = progHashtagService.addProgHashtag(addedProgId, addedHashtagId);

        //then
        Assertions.assertThat(progHashtag.getHashtag()).isSameAs(hashtag);
        Assertions.assertThat(progHashtag.getProg().getProgId()).isEqualTo(addedProgId);
    }

    @Test
    @DisplayName("프로그램_해시태그 삭제")
    @Transactional
    void delete_progHashtag() {
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

        Hashtag hashtag = Hashtag.builder()
                .name("서울")
                .build();
        Long addedHashtagId = hashtagService.addHashtag(hashtag);
        ProgHashtag progHashtag = progHashtagService.addProgHashtag(addedProgId, addedHashtagId);

        //when
        progHashtagService.deleteProgHashtag(progHashtag.getProgHashtagId());

        //then
        Optional<ProgHashtag> foundProgHashtag = progHashtagRepository.findById(progHashtag.getProgHashtagId());
        Assertions.assertThat(foundProgHashtag).isInstanceOf(Optional.class).isNotPresent();
    }
}