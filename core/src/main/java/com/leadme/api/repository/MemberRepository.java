package com.leadme.api.repository;

import com.leadme.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByEmail(String email);
}
