package com.leadme.api.repository.progDaily;

import com.leadme.api.dto.condition.ProgDailySearchCondition;
import com.leadme.api.dto.sdto.ProgDailyProgDto;
import com.leadme.api.dto.sdto.QProgDailyProgDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.leadme.api.entity.QProg.prog;
import static com.leadme.api.entity.QProgDaily.progDaily;

@Repository
public class ProgDailyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ProgDailyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ProgDailyProgDto> findSchedules(ProgDailySearchCondition condition) {
        return queryFactory
                .select(new QProgDailyProgDto(
                        progDaily.progDailyId,
                        progDaily.progDate,
                        prog.progId
                ))
                .from(progDaily)
                .join(progDaily.prog, prog)
                .where(progDaily.prog.progId.eq(condition.getProgId()),
                        progDaily.progDate.substring(0, 8).eq(condition.getProgDate()))
                .fetch();
    }

}
