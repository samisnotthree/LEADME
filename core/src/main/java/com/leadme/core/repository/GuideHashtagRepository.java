package com.leadme.core.repository;

import com.leadme.core.entity.Guide;
import com.leadme.core.entity.GuideHashtag;
import com.leadme.core.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideHashtagRepository extends JpaRepository<GuideHashtag, Long> {
    List<GuideHashtag> findByGuideAndHashtag(Guide guide, Hashtag hashtag);
}
