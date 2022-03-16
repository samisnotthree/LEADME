package com.leadme.core.service;

import com.leadme.core.entity.Hashtag;
import com.leadme.core.repository.HashtagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HashtagServiceTest {

    @Autowired
    HashtagRepository hashtagRepository;
    @Autowired HashtagService hashtagService;


    @Test
    @Transactional
    @DisplayName("해시태그 추가")
    void saveTest() {
        // given
        Hashtag hashtag = Hashtag.builder()
            .name("서울")
            .build();

        // when
        Long addedHashtagId = hashtagService.addHashtag(hashtag);
        Hashtag hashtag1 = hashtagRepository.findById(addedHashtagId).get();

        // then
        assertThat(hashtag1).isSameAs(hashtag);
    }

    @Test
    @Transactional
    @DisplayName("해시태그 삭제(delete)")
    void deleteTest() {
        // given
        Hashtag hashtag = Hashtag.builder()
            .name("서울")
            .build();

        // when
        Long addedHashtagId = hashtagService.addHashtag(hashtag);
        Hashtag foundHashtag = hashtagRepository.findById(addedHashtagId).get();

        hashtagService.deleteHashtag(foundHashtag);

        Optional<Hashtag> foundHashtag2 = hashtagRepository.findById(addedHashtagId);

        assertThat(foundHashtag2).isInstanceOf(Optional.class).isNotPresent();
    }
}
