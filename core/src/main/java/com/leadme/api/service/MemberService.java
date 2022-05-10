package com.leadme.api.service;

import com.leadme.api.entity.Member;
import com.leadme.api.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        member.changeInDate(LocalDateTime.now());
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
        Optional<Member> foundMember = memberRepository.findById(memberId);
        foundMember.ifPresent(member -> member.changeOutDate(LocalDateTime.now()));
    }
}
