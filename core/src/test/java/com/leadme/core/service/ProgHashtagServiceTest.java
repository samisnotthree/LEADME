package com.leadme.core.service;

import com.leadme.core.entity.Prog;
import com.leadme.core.entity.ProgHashtag;
import com.leadme.core.entity.Hashtag;
import com.leadme.core.entity.Member;
import com.leadme.core.repository.ProgHashtagRepository;
import com.leadme.core.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProgHashtagServiceTest {
    @Autowired ProgHashtagService progHashtagService;
    @Autowired ProgHashtagRepository progHashtagRepository;
    @Autowired ProgService progService;
    @Autowired HashtagService hashtagService;

    @Test
    @DisplayName("프로그램_해시태그 등록")
    @Transactional
    void addProgHashtagTest() {
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
    void deleteProgHashtagTest() {
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
        progHashtagService.deleteProgHashtag(progHashtag);
        Optional<ProgHashtag> foundProgHashtag = progHashtagRepository.findById(progHashtag.getProgHashtagId());

        //then
        Assertions.assertThat(foundProgHashtag).isInstanceOf(Optional.class).isNotPresent();
    }
}