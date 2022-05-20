package com.leadme.api.repository.guide;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    List<Guide> findByMember(Member member);
}
