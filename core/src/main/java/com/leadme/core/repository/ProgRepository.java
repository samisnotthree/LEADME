package com.leadme.core.repository;

import com.leadme.core.entity.Prog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgRepository extends JpaRepository<Prog, Long> {
    @Query("select p form Prog p where p.guideId = ?1")
    List<Prog> findByGuideId(String guideId);

}
