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

    @Transactional
    public Long join(Member member) {
        if (!validateDuplicateMember(member)) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        return memberRepository.save(member).getMemberId();
    }

    public boolean validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        return findMembers.isEmpty();
    }

    @Transactional
    public void deleteMember(Member member) {
        member.setOutDate(LocalDateTime.now());

    }
}
