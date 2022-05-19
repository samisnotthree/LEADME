package com.leadme.api.repository.order;

import com.leadme.api.dto.condition.MemberSearchCondition;
import com.leadme.api.dto.condition.OrderSearchCondition;
import com.leadme.api.dto.sdto.MemberGuideDto;
import com.leadme.api.dto.sdto.OrderProgDailyDto;
import com.leadme.api.dto.sdto.QOrderProgDailyDto;
import com.leadme.api.entity.QOrders;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.leadme.api.entity.QMember.member;
import static com.leadme.api.entity.QOrders.orders;
import static com.leadme.api.entity.QProgDaily.progDaily;

@Repository
public class OrderQueryRepository {
    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<OrderProgDailyDto> searchOrdersByProgDailyId(OrderSearchCondition condition, Pageable pageable) {
        List<OrderProgDailyDto> orderList = queryFactory
                .select(new QOrderProgDailyDto(
                        orders.orderId,
                        orders.orderDate,
                        orders.payDate,
                        orders.status.stringValue(),
                        member.memberId,
                        member.email,
                        member.name,
                        member.phone,
                        progDaily.progDailyId,
                        progDaily.progDate,
                        prog.progId,
                        prog.name
                ))
                .from(orders)
                .leftJoin(orders.progDaily, progDaily)
                .leftJoin(orders.member, member)
                .leftJoin(progDaily.prog, prog)
                .where(orders.progDailyId.eq(condition.getProgDailyId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(orders.count())
                .from(orders)
                .leftJoin(orders.progDaily, progDaily)
                //.leftJoin(orders.member, member)
                .where(orders.progDailyId.eq(condition.getProgDailyId()));

        return PageableExecutionUtils.getPage(orderList, pageable, count::fetchOne);
    }
    
    public Page<OrderProgDailyDto> searchOrdersByMemberId(OrderSearchCondition condition, Pageable pageable) {
        List<OrderProgDailyDto> orderList = queryFactory
                .select(new QOrderProgDailyDto(
                        orders.orderId,
                        orders.orderDate,
                        orders.payDate,
                        orders.status.stringValue(),
                        orders.price,
                        orders.payment,
                        member.memberId,
                        member.email,
                        member.name,
                        member.phone,
                        progDaily.progDailyId,
                        progDaily.progDate,
                        prog.progId,
                        prog.name,
                        guide.guideId,
                        guideMember.name
                ))
                .from(orders)
                .leftJoin(orders.progDaily, progDaily)
                .leftJoin(orders.member, member)
                .leftJoin(progDaily.prog, prog)
                .leftJoin(prog.guide, guide)
                .leftJoin(guide.member, guideMember)
                .where(orders.memberId.eq(condition.getMemberId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(orders.count())
                .from(orders)
                //.leftJoin(orders.progDaily, progDaily)
                .leftJoin(orders.member, member)
                .where(orders.memberId.eq(condition.getMemberId()));

        return PageableExecutionUtils.getPage(orderList, pageable, count::fetchOne);
    }

    //사용자명, 사용자이메일, 프로그램명 contains 조회
    public Page<OrderProgDailyDto> searchOrdersByBuilder(OrderSearchCondition condition, Pageable pageable) {
        List<OrderProgDailyDto> orderList = queryFactory
                .select(new QOrderProgDailyDto(
                        orders.orderId,
                        orders.orderDate,
                        orders.payDate,
                        orders.status.stringValue(),
                        orders.price,
                        orders.payment,
                        member.memberId,
                        member.email,
                        member.name,
                        member.phone,
                        progDaily.progDailyId,
                        progDaily.progDate,
                        prog.progId,
                        prog.name,
                        guide.guideId,
                        guideMember.name
                ))
                .from(orders)
                .leftJoin(orders.progDaily, progDaily)
                .leftJoin(orders.member, member)
                .leftJoin(progDaily.prog, prog)
                .leftJoin(prog.guide, guide)
                .leftJoin(guide.member, guideMember)
                //.where(orders.memberId.eq(condition.getMemberId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(orders.count())
                .from(orders)
                //.leftJoin(orders.progDaily, progDaily)
                .leftJoin(orders.member, member)
                //.where(orders.memberId.eq(condition.getMemberId()));

        return PageableExecutionUtils.getPage(orderList, pageable, count::fetchOne);
    }

}
