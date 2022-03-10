package com.leadme.core.service;

import com.leadme.core.entity.Member;
import com.leadme.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     *  사용자 등록
     */
    @Transactional
    public Long joinMember(Member member) {
        if (!validateDuplicateMember(member)) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        member.setInDate(LocalDateTime.now());
        return memberRepository.save(member).getMemberId();
    }

    /**
     *  사용자 중복 체크
     */
    public boolean validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        return findMembers.isEmpty();
    }

    /**
     *  사용자 삭제(update)
     */
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        member.setOutDate(LocalDateTime.now());
    }
}
