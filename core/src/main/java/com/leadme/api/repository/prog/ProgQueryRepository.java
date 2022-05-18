package com.leadme.api.repository.prog;

import com.leadme.api.dto.ProgDto;
import com.leadme.api.dto.condition.ProgSearchCondition;
import com.leadme.api.dto.sdto.ProgGuideMemberDto;
import com.leadme.api.dto.sdto.QProgGuideMemberDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.leadme.api.entity.QGuide.guide;
import static com.leadme.api.entity.QMember.member;
import static com.leadme.api.entity.QProg.prog;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class ProgQueryRepository {
    private final JPAQueryFactory queryFactory;

    public ProgQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<ProgGuideMemberDto> searchProgs(ProgSearchCondition condition, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getName())) {
            builder.or(prog.name.contains(condition.getName()));
        }
        if (hasText(condition.getDesc())) {
            builder.or(prog.desc.contains(condition.getDesc()));
        }

        List<ProgGuideMemberDto> progs = queryFactory
                .select(new QProgGuideMemberDto(
                        prog.progId,
                        prog.name,
                        prog.desc,
                        prog.maxMember,
                        prog.duration,
                        prog.price,
                        prog.meetLocation,
                        guide.guideId,
                        guide.desc,
                        member.memberId,
                        member.name))
                .from(prog)
                .join(prog.guide, guide)
                .join(guide.member, member)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(prog.count())
                .from(prog)
                .where(builder);

        return PageableExecutionUtils.getPage(progs, pageable, count::fetchOne);
    }
}
