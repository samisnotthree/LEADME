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
                .photo("testPhoto")
                .inDate(LocalDateTime.now())
                .outDate(null)
                .guide(null)
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
        Assertions.assertThat(guideHashtag.getGuide()).isSameAs(joinedGuide);
        Assertions.assertThat(guideHashtag.getHashtag().getHashtagId()).isEqualTo(addedHashtagId);
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
                .photo("testPhoto")
                .inDate(LocalDateTime.now())
                .outDate(null)
                .guide(null)
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
        Assertions.assertThat(foundGuideHashtag).isInstanceOf(Optional.class).isNotPresent();
    }
}
