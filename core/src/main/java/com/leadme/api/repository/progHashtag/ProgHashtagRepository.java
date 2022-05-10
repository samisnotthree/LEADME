package com.leadme.api.repository.progHashtag;

import com.leadme.api.entity.Hashtag;
import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgHashtagRepository extends JpaRepository<ProgHashtag, Long> {
    List<ProgHashtag> findByProgAndHashtag(Prog prog, Hashtag hashtag);
}
