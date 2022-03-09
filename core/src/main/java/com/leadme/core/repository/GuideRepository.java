package com.leadme.core.repository;

import com.leadme.core.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    
}
