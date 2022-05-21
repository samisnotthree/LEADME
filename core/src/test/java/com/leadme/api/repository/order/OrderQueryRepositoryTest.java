package com.leadme.api.repository.order;

import com.leadme.api.dto.condition.OrderSearchCondition;
import com.leadme.api.dto.sdto.OrderProgDailyDto;
import com.leadme.api.entity.*;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import com.leadme.dummy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderQueryRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuideRepository guideRepository;
    @Autowired
    ProgRepository progRepository;
    @Autowired
    ProgDailyRepository progDailyRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderQueryRepository orderQueryRepository;

    @BeforeEach
    void init_data() {

    }

    @Test
    @DisplayName("프로그램 일시별 주문내역 조회(가이드용)")
    @Transactional
    void searchOrdersByProgDailyId() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        Prog prog2 = ProgDummy.createProg(2, guide);
        progRepository.save(prog2);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderRepository.save(order);

        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setProgDailyId(progDaily.getProgDailyId());
        Pageable pageable = PageRequest.of(0, 10, Sort.by("orderId").descending());

        //when
        Page<OrderProgDailyDto> orders = orderQueryRepository.searchOrdersByProgDailyId(condition, pageable);

        //then
        System.out.println(orders.getContent().toString());
        assertThat(orders.getContent().size()).isEqualTo(1);
        assertThat(orders).extracting("progDate").containsExactly("202205221530");
    }

    @Test
    @DisplayName("사용자별 주문내역 조회(마이페이지)")
    @Transactional
    void searchOrdersByMemberId() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        Prog prog2 = ProgDummy.createProg(2, guide);
        progRepository.save(prog2);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Member member3 = MemberDummy.createMember(3);
        memberRepository.save(member3);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderRepository.save(order);

        Orders order2 = OrderDummy.createOrder(member3, progDaily);
        orderRepository.save(order2);

        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setMemberId(member2.getMemberId());
        Pageable pageable = PageRequest.of(0, 10, Sort.by("orderId").descending());

        //when
        Page<OrderProgDailyDto> orders = orderQueryRepository.searchOrdersByMemberId(condition, pageable);

        //then
        System.out.println(orders.getContent().toString());
        assertThat(orders.getContent().size()).isEqualTo(1);
        assertThat(orders).extracting("progDate").containsExactly("202205221530");
        assertThat(orders).extracting("name").containsExactly("김멤버2");
        assertThat(orders).extracting("guideName").containsExactly("김멤버1");
    }

    @Test
    @DisplayName("검색조건(사용자명, 사용자이메일, 프로그램명)에 따른 주문내역 조회(관리자)")
    @Transactional
    void searchOrdersByCondition() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        Prog prog2 = ProgDummy.createProg(2, guide);
        progRepository.save(prog2);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Member member3 = MemberDummy.createMember(3);
        memberRepository.save(member3);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderRepository.save(order);

        Orders order2 = OrderDummy.createOrder(member3, progDaily);
        orderRepository.save(order2);

        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setEmail("kim2");
        condition.setName("kim2");
        condition.setProgName("kim2");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("orderId").descending());

        //when
        Page<OrderProgDailyDto> orders = orderQueryRepository.searchOrdersByCondition(condition, pageable);

        //then
        System.out.println(orders.getContent().toString());
        assertThat(orders.getContent().size()).isEqualTo(1);
        assertThat(orders).extracting("progDate").containsExactly("202205221530");
        assertThat(orders).extracting("name").containsExactly("김멤버2");
        assertThat(orders).extracting("guideName").containsExactly("김멤버1");
    }

    @Test
    @DisplayName("검색조건(사용자명, 사용자이메일, 프로그램명)에 따른 주문내역 조회(관리자) 없는 정보로 검색")
    @Transactional
    void searchOrdersByConditionExpectNothing() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        Prog prog2 = ProgDummy.createProg(2, guide);
        progRepository.save(prog2);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Member member3 = MemberDummy.createMember(3);
        memberRepository.save(member3);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderRepository.save(order);

        Orders order2 = OrderDummy.createOrder(member3, progDaily);
        orderRepository.save(order2);

        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setEmail("kim4");
        condition.setName("kim4");
        condition.setProgName("kim4");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("orderId").descending());

        //when
        Page<OrderProgDailyDto> orders = orderQueryRepository.searchOrdersByCondition(condition, pageable);

        //then
        System.out.println(orders.getContent().toString());
        assertThat(orders.getContent().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("검색조건(사용자명, 사용자이메일, 프로그램명)에 따른 주문내역 조회(관리자) 공통 검색어")
    @Transactional
    void searchOrdersByConditionCommon() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        Prog prog2 = ProgDummy.createProg(2, guide);
        progRepository.save(prog2);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Member member3 = MemberDummy.createMember(3);
        memberRepository.save(member3);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderRepository.save(order);

        Orders order2 = OrderDummy.createOrder(member3, progDaily);
        orderRepository.save(order2);

        OrderSearchCondition condition = new OrderSearchCondition();
        condition.setEmail("김멤");
        condition.setName("김멤");
        condition.setProgName("김멤");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("orderId").descending());

        //when
        Page<OrderProgDailyDto> orders = orderQueryRepository.searchOrdersByCondition(condition, pageable);

        //then
        System.out.println(orders.getContent().toString());
        assertThat(orders.getContent().size()).isEqualTo(2);
        assertThat(orders).extracting("name").containsExactly("김멤버2", "김멤버3");
    }

}
