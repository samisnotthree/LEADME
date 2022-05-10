package com.leadme.api.repository.guide;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    List<Guide> findByMember(Member member);
}
