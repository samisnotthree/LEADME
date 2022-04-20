package com.leadme.core.repository;

import com.leadme.core.entity.Prog;
import com.leadme.core.entity.ProgDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProgDailyRepository extends JpaRepository<ProgDaily, Long> {
    List<ProgDaily> findByProgAndProgDate(Prog prog, LocalDateTime progDate);
    
    @Query(value = "select pd from ProgDaily pd join fetch pd.prog p where p.progId = :id and formatdatetime(pd.progDate, 'yyyyMMdd') = :progDate")
    public List<ProgDaily> findSchedules(@Param(value = "id") Long progId, @Param(value = "progDate") String progDate);
}
