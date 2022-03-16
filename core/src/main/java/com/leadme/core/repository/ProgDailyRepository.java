package com.leadme.core.repository;

import com.leadme.core.entity.Prog;
import com.leadme.core.entity.ProgDaily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProgDailyRepository extends JpaRepository<ProgDaily, Long> {
    List<ProgDaily> findByProgAndProgDate(Prog prog, LocalDateTime progDate);
}
