package com.leadme.api.service;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import com.leadme.api.repository.GuideRepository;
import com.leadme.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final MemberRepository memberRepository;

    /**
     *  가이드 등록
     */
    @Transactional
    public Guide joinGuide(Long memberId, String desc) {
        Member member = memberRepository.findById(memberId).get();

        if (isJoinedGuide(member)) {
            throw new IllegalStateException("이미 가이드로 등록되어 있습니다.");
        }

        Guide guide = Guide.builder()
            .desc(desc)
            .inDate(LocalDateTime.now())
            .outDate(null)
            .member(member)
            .build();

        return guideRepository.save(guide);
    }

    /**
     *  가이드 등록 여부 조회
     */
    public boolean isJoinedGuide(Member member) {
        return !guideRepository.findByMember(member).isEmpty();
    }

    @Transactional
    public void deleteGuide(Long guideId) {
        Optional<Guide> foundGuide = guideRepository.findById(guideId);
        foundGuide.ifPresent(guide -> guide.changeOutDate(LocalDateTime.now()));
    }
}
