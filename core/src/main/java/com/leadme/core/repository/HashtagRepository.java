package com.leadme.core.repository;

import com.leadme.core.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository  extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findByName(String name);
}
