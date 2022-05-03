package com.leadme.api.service;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.GuideHashtag;
import com.leadme.api.entity.Hashtag;
import com.leadme.api.repository.GuideHashtagRepository;
import com.leadme.api.repository.GuideRepository;
import com.leadme.api.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
