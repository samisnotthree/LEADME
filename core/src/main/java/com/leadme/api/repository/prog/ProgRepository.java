package com.leadme.api.repository.prog;

import com.leadme.api.entity.Prog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgRepository extends JpaRepository<Prog, Long>, ProgRepositoryCustom {
    List<Prog> findByDescContaining(String desc);
}
