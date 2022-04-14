package com.leadme.core.service;

import com.leadme.core.entity.Guide;
import com.leadme.core.entity.GuideHashtag;
import com.leadme.core.entity.Hashtag;
import com.leadme.core.repository.GuideHashtagRepository;
import com.leadme.core.repository.GuideRepository;
import com.leadme.core.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuideHashtagService {
    private final GuideHashtagRepository guideHashtagRepository;
    private final GuideRepository guideRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public GuideHashtag addGuideHashtag(Long guideId, Long hashtagId) {
        Guide foundGuide = guideRepository.findById(guideId).get();
        Hashtag foundHashtag = hashtagRepository.findById(hashtagId).get();

        if (isExistGuideHashtag(foundGuide, foundHashtag)) {
            throw new IllegalStateException("이미 등록되어 있는 해시태그입니다.");
        }

        GuideHashtag guideHashtag = GuideHashtag.builder()
                                                .guide(foundGuide)
                                                .hashtag(foundHashtag)
                                                .build();

        return guideHashtagRepository.save(guideHashtag);
    }

    public boolean isExistGuideHashtag(Guide guide, Hashtag hashtag) {
        return !guideHashtagRepository.findByGuideAndHashtag(guide, hashtag).isEmpty();
    }

    @Transactional
    public void deleteGuideHashtag(Long guideHashtagId) {
        guideHashtagRepository.deleteById(guideHashtagId);
    }
}
