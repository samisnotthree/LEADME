package com.leadme.api.repository.member;

import com.leadme.api.dto.MemberDto;
import com.leadme.api.dto.MemberSearchCondition;
import com.leadme.api.dto.QMemberDto;
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
import static org.springframework.util.StringUtils.hasText;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberDto> searchMembers(MemberSearchCondition condition, Pageable pageable) {
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
                        guide))
                .from(member)
                .leftJoin(member.guide, guide)
                .where(emailStartsWith(condition.getEmail())
                        .or(nameStartsWith(condition.getName())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(member.count())
                .from(member)
                .where(emailStartsWith(condition.getEmail())
                        .or(nameStartsWith(condition.getName())));
        
        return PageableExecutionUtils.getPage(members, pageable, count::fetchOne);
    }

    private BooleanExpression nameStartsWith(String name) {
        return hasText(name) ? member.name.startsWith(name) : null;
    }

    private BooleanExpression emailStartsWith(String email) {
        return hasText(email) ? member.email.startsWith(email) : null;
    }
}
