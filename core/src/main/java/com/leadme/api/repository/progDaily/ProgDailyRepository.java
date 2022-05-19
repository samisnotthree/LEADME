package com.leadme.api.repository.progDaily;

import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgDaily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgDailyRepository extends JpaRepository<ProgDaily, Long> {
    List<ProgDaily> findByProgAndProgDate(Prog prog, String progDate);
    
//    @Query(value = "select pd from ProgDaily pd join fetch pd.prog p where p.progId = :id and formatdatetime(pd.progDate, 'yyyyMMdd') = :progDate")
//    public List<ProgDaily> findSchedules(@Param(value = "id") Long progId, @Param(value = "progDate") String progDate);
}
