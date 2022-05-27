package com.leadme.api.service;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.GuideHashtag;
import com.leadme.api.entity.Hashtag;
import com.leadme.api.entity.Member;
import com.leadme.api.repository.guideHashtag.GuideHashtagRepository;
import com.leadme.api.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GuideHashtagServiceTest {

    @Autowired GuideHashtagService guideHashtagService;
    @Autowired GuideHashtagRepository guideHashtagRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired GuideService guideService;
    @Autowired HashtagService hashtagService;

    @Test
    @DisplayName("가이드_해시태그 등록")
    @Transactional
    void add_guideHashtag() {
        //given
        Member member = Member.builder()
                .email("test@test.com")
                .name("testName")
                .pass("testPass")
                .phone("testPhone")
                .inDate(LocalDateTime.now())
                .build();

        Member savedMember = memberRepository.save(member);
        String desc = "testDesc";
        Guide joinedGuide = guideService.joinGuide(savedMember.getMemberId(), desc);

        Hashtag hashtag = Hashtag.builder()
                .name("서울")
                .build();
        Long addedHashtagId = hashtagService.addHashtag(hashtag);

        //when
        GuideHashtag guideHashtag = guideHashtagService.addGuideHashtag(joinedGuide.getGuideId(), addedHashtagId);

        //then
        assertThat(guideHashtag.getGuide()).isSameAs(joinedGuide);
        assertThat(guideHashtag.getHashtag().getHashtagId()).isSameAs(addedHashtagId);
    }

    @Test
    @DisplayName("가이드_해시태그 중복 등록")
    @Transactional
    void add_guideHashtag_duplicate() {
        //given
        Member member = Member.builder()
                .email("test@test.com")
                .name("testName")
                .pass("testPass")
                .phone("testPhone")
                .inDate(LocalDateTime.now())
                .build();

        Member savedMember = memberRepository.save(member);
        String desc = "testDesc";
        Guide joinedGuide = guideService.joinGuide(savedMember.getMemberId(), desc);

        Hashtag hashtag = Hashtag.builder()
                .name("서울")
                .build();
        Long addedHashtagId = hashtagService.addHashtag(hashtag);
        guideHashtagService.addGuideHashtag(joinedGuide.getGuideId(), addedHashtagId);

        //when
        Throwable exception = catchThrowable(() -> {
            guideHashtagService.addGuideHashtag(joinedGuide.getGuideId(), addedHashtagId);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("이미 등록되어 있는 해시태그입니다.");
    }

    @Test
    @DisplayName("가이드_해시태그 등록 null 가이드")
    @Transactional
    void add_guideHashtag_null_guide() {
        //given
        Hashtag hashtag = Hashtag.builder()
                .name("서울")
                .build();
        Long addedHashtagId = hashtagService.addHashtag(hashtag);

        //when
        Throwable exception = catchThrowable(() -> {
            guideHashtagService.addGuideHashtag(null, addedHashtagId);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("가이드 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("가이드_해시태그 등록 없는 가이드")
    @Transactional
    void add_guideHashtag_not_exists_guide() {
        //given
        Hashtag hashtag = Hashtag.builder()
                .name("서울")
                .build();
        Long addedHashtagId = hashtagService.addHashtag(hashtag);

        //when
        Throwable exception = catchThrowable(() -> {
            guideHashtagService.addGuideHashtag(Long.MAX_VALUE, addedHashtagId);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 가이드입니다.");
    }

    @Test
    @DisplayName("가이드_해시태그 등록 null 해시태그")
    @Transactional
    void add_guideHashtag_null_hashtag() {
        //given
        Member member = Member.builder()
                .email("test@test.com")
                .name("testName")
                .pass("testPass")
                .phone("testPhone")
                .inDate(LocalDateTime.now())
                .build();

        Member savedMember = memberRepository.save(member);
        String desc = "testDesc";
        Guide joinedGuide = guideService.joinGuide(savedMember.getMemberId(), desc);

        //when
        Throwable exception = catchThrowable(() -> {
            guideHashtagService.addGuideHashtag(joinedGuide.getGuideId(), null);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("해시태그 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("가이드_해시태그 등록 없는 해시태그")
    @Transactional
    void add_guideHashtag_not_exists_hashtag() {
        //given
        Member member = Member.builder()
                .email("test@test.com")
                .name("testName")
                .pass("testPass")
                .phone("testPhone")
                .inDate(LocalDateTime.now())
                .build();

        Member savedMember = memberRepository.save(member);
        String desc = "testDesc";
        Guide joinedGuide = guideService.joinGuide(savedMember.getMemberId(), desc);

        //when
        Throwable exception = catchThrowable(() -> {
            guideHashtagService.addGuideHashtag(joinedGuide.getGuideId(), Long.MAX_VALUE);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 해시태그입니다.");
    }

    @Test
    @DisplayName("가이드_해시태그 삭제")
    @Transactional
    void delete_guideHashtag() {
        //given
        Member member = Member.builder()
                .email("test@test.com")
                .name("testName")
                .pass("testPass")
                .phone("testPhone")
                .inDate(LocalDateTime.now())
                .build();

        Member savedMember = memberRepository.save(member);
        String desc = "testDesc";
        Guide joinedGuide = guideService.joinGuide(savedMember.getMemberId(), desc);

        Hashtag hashtag = Hashtag.builder()
                .name("서울")
                .build();
        Long addedHashtagId = hashtagService.addHashtag(hashtag);
        GuideHashtag guideHashtag = guideHashtagService.addGuideHashtag(joinedGuide.getGuideId(), addedHashtagId);

        //when
        guideHashtagService.deleteGuideHashtag(guideHashtag.getGuideHashtagId());

        //then
        Optional<GuideHashtag> foundGuideHashtag = guideHashtagRepository.findById(guideHashtag.getGuideHashtagId());
        assertThat(foundGuideHashtag).isInstanceOf(Optional.class).isNotPresent();
    }
}
