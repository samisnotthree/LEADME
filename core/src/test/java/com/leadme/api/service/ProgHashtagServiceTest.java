package com.leadme.api.service;

import com.leadme.api.dummy.HashtagDummy;
import com.leadme.api.entity.*;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.hashtag.HashtagRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.repository.progHashtag.ProgHashtagRepository;
import com.leadme.dummy.GuideDummy;
import com.leadme.dummy.MemberDummy;
import com.leadme.dummy.ProgDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class ProgHashtagServiceTest {
    @Autowired ProgHashtagService progHashtagService;
    @Autowired ProgHashtagRepository progHashtagRepository;
    @Autowired ProgService progService;
    @Autowired HashtagService hashtagService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuideRepository guideRepository;
    @Autowired
    ProgRepository progRepository;
    @Autowired
    HashtagRepository hashtagRepository;

    @Test
    @DisplayName("프로그램_해시태그 등록")
    @Transactional
    void add_progHashtag() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        Hashtag hashtag = HashtagDummy.createHashtag("서울");
        hashtagRepository.save(hashtag);

        //when
        ProgHashtag progHashtag = progHashtagService.addProgHashtag(prog.getProgId(), hashtag.getHashtagId());

        //then
        assertThat(progHashtag.getHashtag()).isSameAs(hashtag);
        assertThat(progHashtag.getProg().getProgId()).isEqualTo(prog.getProgId());
    }

    @Test
    @DisplayName("프로그램_해시태그 등록 null 프로그램")
    @Transactional
    void add_progHashtag_null_prog() {
        //given
        Hashtag hashtag = HashtagDummy.createHashtag("서울");
        hashtagRepository.save(hashtag);

        //when
        Throwable exception = catchThrowable(() -> {
            progHashtagService.addProgHashtag(null, hashtag.getHashtagId());
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("프로그램 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("프로그램_해시태그 등록 없는 프로그램")
    @Transactional
    void add_progHashtag_not_exists_prog() {
        //given
        Hashtag hashtag = HashtagDummy.createHashtag("서울");
        hashtagRepository.save(hashtag);

        //when
        Throwable exception = catchThrowable(() -> {
            progHashtagService.addProgHashtag(Long.MAX_VALUE, hashtag.getHashtagId());
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 프로그램입니다.");
    }

    @Test
    @DisplayName("프로그램_해시태그 등록 null 해시태그")
    @Transactional
    void add_progHashtag_null_hashtag() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        //when
        Throwable exception = catchThrowable(() -> {
            progHashtagService.addProgHashtag(prog.getProgId(), null);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("해시태그 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("프로그램_해시태그 등록 없는 해시태그")
    @Transactional
    void add_progHashtag_not_exists_hashtag() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        //when
        Throwable exception = catchThrowable(() -> {
            progHashtagService.addProgHashtag(prog.getProgId(), Long.MAX_VALUE);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 해시태그입니다.");
    }

    @Test
    @DisplayName("프로그램_해시태그 중복 등록")
    @Transactional
    void add_progHashtag_duplicate() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        Hashtag hashtag = HashtagDummy.createHashtag("서울");
        hashtagRepository.save(hashtag);

        progHashtagService.addProgHashtag(prog.getProgId(), hashtag.getHashtagId());

        //when
        Throwable exception = catchThrowable(() -> {
            progHashtagService.addProgHashtag(prog.getProgId(), hashtag.getHashtagId());
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("이미 등록되어 있는 해시태그입니다.");
    }

    @Test
    @DisplayName("프로그램_해시태그 삭제")
    @Transactional
    void delete_progHashtag() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        Hashtag hashtag = HashtagDummy.createHashtag("서울");
        hashtagRepository.save(hashtag);

        ProgHashtag progHashtag = progHashtagService.addProgHashtag(prog.getProgId(), hashtag.getHashtagId());

        //when
        progHashtagService.deleteProgHashtag(progHashtag.getProgHashtagId());

        //then
        Optional<ProgHashtag> foundProgHashtag = progHashtagRepository.findById(progHashtag.getProgHashtagId());
        assertThat(foundProgHashtag).isInstanceOf(Optional.class).isNotPresent();
    }

    @Test
    @DisplayName("프로그램_해시태그 삭제 null 프로그램 해시태그")
    @Transactional
    void delete_progHashtag_null() {
        //when
        Throwable exception = catchThrowable(() -> {
            progHashtagService.deleteProgHashtag(null);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("프로그램 해시태그 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("프로그램_해시태그 삭제 없는 프로그램 해시태그")
    @Transactional
    void delete_progHashtag_not_exists() {
        //when
        Throwable exception = catchThrowable(() -> {
            progHashtagService.deleteProgHashtag(Long.MAX_VALUE);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 프로그램 해시태그입니다.");
    }
}