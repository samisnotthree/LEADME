package com.leadme.core.service;

import com.leadme.core.entity.Guide;
import com.leadme.core.entity.Member;
import com.leadme.core.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;

    /**
     *  가이드 등록
     */
    @Transactional
    public Long joinGuide(Guide guide) {
        return guideRepository.save(guide).getGuideId();
    }

    /**
     *  가이드 등록 여부 조회
     */
    public boolean isJoinedGuide(Member member) {
        return !guideRepository.findByMember(member).isEmpty();
    }


}
