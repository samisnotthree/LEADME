package com.leadme.api.service;

import com.leadme.api.entity.Hashtag;
import com.leadme.api.repository.hashtag.HashtagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class HashtagServiceTest {

    @Autowired
    HashtagRepository hashtagRepository;
    @Autowired HashtagService hashtagService;

    @Test
    @Transactional
    @DisplayName("해시태그 추가")
    void add_hashtag() {
        // given
        Hashtag hashtag = Hashtag.builder()
            .name("서울")
            .build();

        // when
        Long addedHashtagId = hashtagService.addHashtag(hashtag);

        // then
        assertThat(addedHashtagId).isSameAs(hashtag.getHashtagId());
    }

    @Test
    @Transactional
    @DisplayName("해시태그 중복 추가")
    void add_hashtag_duplicate() {
        // given
        Hashtag hashtag = Hashtag.builder()
            .name("서울")
            .build();
        Hashtag hashtag2 = Hashtag.builder()
            .name("서울")
            .build();
        hashtagService.addHashtag(hashtag);

        // when
        Throwable exception = catchThrowable(() -> {
            hashtagService.addHashtag(hashtag2);
        });

        // then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("이미 존재하는 해시태그입니다.");
    }
    
    @Test
    @Transactional
    @DisplayName("해시태그 추가 null 해시태그명")
    void add_hashtag_null() {
        // given
        Hashtag hashtag = Hashtag.builder()
            .name(null)
            .build();

        // when
        Throwable exception = catchThrowable(() -> {
            hashtagService.addHashtag(hashtag);
        });

        // then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("해시태그명은 필수 입력 값입니다.");
    }

    @Test
    @Transactional
    @DisplayName("해시태그 삭제(delete)")
    void delete_hashtag() {
        // given
        Hashtag hashtag = Hashtag.builder()
            .name("서울")
            .build();
        Long addedHashtagId = hashtagService.addHashtag(hashtag);

        // when
        hashtagService.deleteHashtag(addedHashtagId);

        // then
        Optional<Hashtag> foundHashtagAfterDelete = hashtagRepository.findById(addedHashtagId);
        assertThat(foundHashtagAfterDelete).isInstanceOf(Optional.class).isNotPresent();
    }

    @Test
    @Transactional
    @DisplayName("해시태그 삭제(delete) null 해시태그")
    void delete_hashtag_null() {
        // when
        Throwable exception = catchThrowable(() -> {
            hashtagService.deleteHashtag(null);
        });

        // then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("해시태그 정보가 올바르지 않습니다.");
    }

    @Test
    @Transactional
    @DisplayName("해시태그 삭제(delete) 없는 해시태그")
    void delete_hashtag_not_exists() {
        // when
        Throwable exception = catchThrowable(() -> {
            hashtagService.deleteHashtag(Long.MAX_VALUE);
        });

        // then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 해시태그입니다.");
    }

    @Test
    @Transactional
    @DisplayName("해시태그 조회")
    void find_hashtags() {
        // given
        Hashtag hashtag = Hashtag.builder()
                .name("서울")
                .build();
        Hashtag hashtag2 = Hashtag.builder()
                .name("야간")
                .build();
        hashtagService.addHashtag(hashtag);
        hashtagService.addHashtag(hashtag2);

        // when
        List<Hashtag> hashtags = hashtagRepository.findAll();

        //then
        assertThat(hashtags).extracting("name").containsExactly("서울", "야간");
    }
}
