package com.leadme.api.service;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        Optional.ofNullable(memberId).orElseThrow(() -> new IllegalStateException("사용자 정보가 올바르지 않습니다."));

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));

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
        Optional.ofNullable(guideId).orElseThrow(() -> new IllegalStateException("가이드 정보가 올바르지 않습니다."));
        
        Optional<Guide> foundGuide = guideRepository.findById(guideId);
        foundGuide.ifPresentOrElse(
                g -> g.changeOutDate(LocalDateTime.now()),
                () -> foundGuide.orElseThrow(() -> new IllegalStateException("존재하지 않는 가이드입니다.")));
        foundGuide.ifPresent(guide -> guide.changeOutDate(LocalDateTime.now()));
    }
}
