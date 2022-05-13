package com.leadme.api.repository.member;

import com.leadme.api.dto.MemberDto;
import com.leadme.api.dto.MemberSearchCondition;
import com.leadme.api.dto.QMemberDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import static org.springframework.util.StringUtils.hasText;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<MemberDto> searchMembers(MemberSearchCondition condition, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getName())) {
            builder.or(member.name.startsWith(condition.getName()));
        }

        if (hasText(condition.getEmail())) {
            builder.or(member.email.startsWith(condition.getEmail()));
        }

        List<MemberDto> members = queryFactory
                .select(new QMemberDto(
                        member.memberId,
                        member.email,
                        member.name,
                        member.pass,
                        member.photo,
                        member.phone,
                        member.inDate,
                        member.outDate,
                        guide.guideId,
                        guide.desc))
                .from(member)
                .leftJoin(member.guide, guide)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(member.count())
                .from(member)
                .where(builder);

        return PageableExecutionUtils.getPage(members, pageable, count::fetchOne);
    }

    private BooleanExpression nameStartsWith(String name) {
        return hasText(name) ? member.name.startsWith(name) : null;
    }

    private BooleanExpression emailStartsWith(String email) {
        return hasText(email) ? member.email.startsWith(email) : null;
    }

}
