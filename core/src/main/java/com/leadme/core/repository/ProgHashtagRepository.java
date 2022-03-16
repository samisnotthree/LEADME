package com.leadme.core.repository;

import com.leadme.core.entity.Hashtag;
import com.leadme.core.entity.Prog;
import com.leadme.core.entity.ProgHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgHashtagRepository extends JpaRepository<ProgHashtag, Long> {
    List<ProgHashtag> findByProgAndHashtag(Prog prog, Hashtag hashtag);
}
