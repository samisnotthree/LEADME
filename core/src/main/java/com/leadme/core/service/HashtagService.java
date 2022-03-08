package com.leadme.core.service;

import com.leadme.core.entity.Hashtag;
import com.leadme.core.entity.Member;
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

    @Transactional
    private Long save(Hashtag hashtag) {
        if (!validateDuplicateHashtag(hashtag)) {
            throw new IllegalStateException("이미 존재하는 해시태그입니다.");
        }
        return hashtagRepository.save(hashtag).getHashtagId();
    }

    public boolean validateDuplicateHashtag(Hashtag hashtag) {
        List<Hashtag> findHashtags = hashtagRepository.findByName(hashtag.getName());
        return findHashtags.isEmpty();
    }

    @Transactional
    public void delete(Hashtag hashtag) {
        hashtagRepository.delete(hashtag);
    }
}
