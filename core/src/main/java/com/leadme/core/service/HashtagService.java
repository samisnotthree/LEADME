package com.leadme.core.service;

import com.leadme.core.entity.Hashtag;
import com.leadme.core.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    /**
     *  해시태그 등록
     */
    @Transactional
    public Long addHashtag(Hashtag hashtag) {
        if (!validateDuplicateHashtag(hashtag)) {
            throw new IllegalStateException("이미 존재하는 해시태그입니다.");
        }
        return hashtagRepository.save(hashtag).getHashtagId();
    }

    /**
     *  해시태그 중복 체크
     */
    public boolean validateDuplicateHashtag(Hashtag hashtag) {
        List<Hashtag> findHashtags = hashtagRepository.findByName(hashtag.getName());
        return findHashtags.isEmpty();
    }

    /**
     *  해시태그 삭제(delete)
     */
    @Transactional
    public void deleteHashtag(Hashtag hashtag) {
        hashtagRepository.delete(hashtag);
    }
}
