package com.leadme.api.repository.progDaily;

import com.leadme.api.dto.ProgDailyDto;
import com.leadme.api.dto.ProgDailySearchCondition;
import com.leadme.api.dto.QProgDailyDto;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.leadme.api.entity.QProgDaily.progDaily;

@Repository
public class ProgDailyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ProgDailyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ProgDailyDto> findSchedules(ProgDailySearchCondition condition) {
        return queryFactory
                .select(new QProgDailyDto(
                        progDaily.progDailyId,
                        progDaily.progDate,
                        progDaily.prog.progId
                ))
                .from(progDaily)
                .where(progDaily.prog.progId.eq(condition.getProgId()),
                        progDaily.progDate.substring(0, 8).eq(condition.getProgDate()))
                .fetch();
    }

}
