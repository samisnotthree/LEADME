package com.leadme.api.repository.guideHashtag;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.GuideHashtag;
import com.leadme.api.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideHashtagRepository extends JpaRepository<GuideHashtag, Long> {
    List<GuideHashtag> findByGuideAndHashtag(Guide guide, Hashtag hashtag);
}
