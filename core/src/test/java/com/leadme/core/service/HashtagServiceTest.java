package com.leadme.core.service;

import com.leadme.core.entity.Hashtag;
import com.leadme.core.repository.HashtagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HashtagServiceTest {

    @Autowired
    HashtagRepository hashtagRepository;


    @Test
    @Transactional
    @DisplayName("해시태그 추가")
    void saveTest() {
        // given
        Hashtag hashtag = new Hashtag();
        hashtag.setName("서울");

        // when
        Hashtag savedHashtag = hashtagRepository.save(hashtag);
        Hashtag hashtag1 = hashtagRepository.findById(1L).get();

        // then
        assertThat(savedHashtag.getName()).isEqualTo("서울");
        assertThat(hashtag1).isEqualTo(savedHashtag);
    }

    @Test
    @Transactional
    @DisplayName("해시태그 삭제(delete)")
    void deleteTest() {
        // given
        Hashtag hashtag = new Hashtag();
        hashtag.setName("서울");

        // when
        Hashtag savedHashtag = hashtagRepository.save(hashtag);
        hashtagRepository.delete(savedHashtag);
        System.out.println("@@@@");
        //Hashtag hashtag1 = hashtagRepository.findById(1L).get();

        // then
        // 좋은 테스트가 아님
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
            hashtagRepository.findById(1L).get();
        });
    }
}