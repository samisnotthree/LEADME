package com.leadme.core.service;

import com.leadme.core.entity.Hashtag;
import com.leadme.core.repository.HashtagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        hashtagService.deleteHashtag(addedHashtagId);

        Optional<Hashtag> foundHashtag2 = hashtagRepository.findById(addedHashtagId);

        assertThat(foundHashtag2).isInstanceOf(Optional.class).isNotPresent();
    }

    //TODO findPopularHashtags 로 바꾸기
    @Test
    @Transactional
    @DisplayName("해시태그 조회")
    void findHashtagsTest() {
        // given
        Hashtag hashtag = Hashtag.builder()
                .name("서울")
                .build();
        Hashtag hashtag2 = Hashtag.builder()
                .name("야간")
                .build();

        // when
        Long addedHashtagId = hashtagService.addHashtag(hashtag);
        Long addedHashtagId2 = hashtagService.addHashtag(hashtag2);

        List<Hashtag> hashtags = hashtagRepository.findAll();

        Set<String> nameSet = new HashSet<>();
        nameSet.add("서울");
        nameSet.add("야간");

        Set<String> foundNameSet = new HashSet<>();

        Iterator<Hashtag> iterator = hashtags.iterator();
        while(iterator.hasNext()) {
            foundNameSet.add(iterator.next().getName());
        }

        //then
        assertThat(nameSet).isEqualTo(foundNameSet);
    }
}
