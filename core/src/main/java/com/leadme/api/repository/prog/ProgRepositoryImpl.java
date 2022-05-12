package com.leadme.api.repository.prog;

import com.leadme.api.dto.ProgDto;
import com.leadme.api.dto.ProgSearchCondition;
import com.leadme.api.dto.QProgDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.leadme.api.entity.QGuide.guide;
import static com.leadme.api.entity.QMember.member;
import static com.leadme.api.entity.QProg.prog;
import static org.springframework.util.StringUtils.hasText;

public class ProgRepositoryImpl implements ProgRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ProgRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ProgDto> searchProgs(ProgSearchCondition condition, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getName())) {
            builder.or(prog.name.contains(condition.getName()));
        }
        if (hasText(condition.getDesc())) {
            builder.or(prog.desc.contains(condition.getDesc()));
        }

        List<ProgDto> progs = queryFactory
                .select(new QProgDto(
                        prog.progId,
                        prog.name,
                        prog.desc,
                        prog.maxMember,
                        prog.duration,
                        prog.price,
                        prog.meetLocation,
                        prog.inDate,
                        guide.guideId,
                        guide.desc,
                        member.memberId,
                        member.name))
                .from(prog)
                .leftJoin(prog.guide, guide)
                .leftJoin(guide.member, member)
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

    BooleanExpression nameLike(String name) {
        return hasText(name) ? prog.name.like("%" + name + "%") : null;
    }

    BooleanExpression descLike(String desc) {
        return hasText(desc) ? prog.desc.like("%" + desc + "%") : null;
    }
}
