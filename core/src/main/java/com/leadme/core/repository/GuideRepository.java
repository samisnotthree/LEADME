package com.leadme.core.repository;

import com.leadme.core.entity.Guide;
import com.leadme.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    List<Guide> findByMember(Member member);
}
