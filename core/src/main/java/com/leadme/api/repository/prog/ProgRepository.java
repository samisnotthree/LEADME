package com.leadme.api.repository.prog;

import com.leadme.api.entity.Prog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgRepository extends JpaRepository<Prog, Long> {

}
