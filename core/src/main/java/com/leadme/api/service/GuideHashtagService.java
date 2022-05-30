package com.leadme.api.service;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.GuideHashtag;
import com.leadme.api.entity.Hashtag;
import com.leadme.api.repository.guideHashtag.GuideHashtagRepository;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.hashtag.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuideHashtagService {
    private final GuideHashtagRepository guideHashtagRepository;
    private final GuideRepository guideRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public GuideHashtag addGuideHashtag(Long guideId, Long hashtagId) {
        Optional.ofNullable(guideId).orElseThrow(() -> new IllegalStateException("가이드 정보가 올바르지 않습니다."));
        Optional.ofNullable(hashtagId).orElseThrow(() -> new IllegalStateException("해시태그 정보가 올바르지 않습니다."));
        
        Guide foundGuide = guideRepository.findById(guideId).orElseThrow(() -> new IllegalStateException("존재하지 않는 가이드입니다."));
        Hashtag foundHashtag = hashtagRepository.findById(hashtagId).orElseThrow(() -> new IllegalStateException("존재하지 않는 해시태그입니다."));

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
        Optional.ofNullable(guideHashtagId).orElseThrow(() -> new IllegalStateException("가이드 해시태그 정보가 올바르지 않습니다."));
        Optional<GuideHashtag> guideHashtag = guideHashtagRepository.findById(guideHashtagId);
        guideHashtag.ifPresentOfElse(
            gh -> guideHashtagRepository.delete(gh),
            () -> guideHashtag.orElseThrow(() -> new IllegalStateException("존재하지 않는 가이드 해시태그입니다."))
        );
    }
}
