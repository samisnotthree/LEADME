package com.leadme.core.repository;

import com.leadme.core.entity.Prog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgRepository extends JpaRepository<Prog, Long> {
    List<Prog> findByDescContaining(String desc);
}
