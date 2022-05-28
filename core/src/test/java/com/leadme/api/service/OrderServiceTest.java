package com.leadme.api.service;

import com.leadme.api.entity.*;
import com.leadme.api.entity.enums.OrderStatus;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.order.OrderRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import com.leadme.dummy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired ProgDailyService progDailyService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuideRepository guideRepository;
    @Autowired
    ProgRepository progRepository;
    @Autowired
    ProgDailyRepository progDailyRepository;

    @Test
    @DisplayName("주문 등록")
    @Transactional
    void add_order() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = Orders.builder()
                .price(50000L)
                .payment("카드")
                .orderDate(LocalDateTime.now())
                .payDate(LocalDateTime.now())
                .status(OrderStatus.PAYED)
                .progDaily(progDaily)
                .member(member2)
                .build();

        //when
        Long addedOrder = orderService.addOrder(order);

        //then
        Orders foundOrder = orderRepository.findById(addedOrder).get();
        assertThat(foundOrder).isSameAs(order);
    }

    @Test
    @DisplayName("주문 등록 null 멤버")
    @Transactional
    void add_order_null_member() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = OrderDummy.createOrder(null, progDaily);

        //when
        Throwable exception = catchThrowable(() -> {
            orderService.addOrder(order);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("사용자 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("주문 등록 없는 멤버")
    @Transactional
    void add_order_not_exists_member() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);
        memberRepository.deleteById(member2.getMemberId());

        Orders order = OrderDummy.createOrder(member2, progDaily);

        //when
        Throwable exception = catchThrowable(() -> {
            orderService.addOrder(order);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 사용자입니다.");
    }


    @Test
    @DisplayName("주문 등록 null 프로그램 일정")
    @Transactional
    void add_order_null_progDaily() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = OrderDummy.createOrder(member2, null);

        //when
        Throwable exception = catchThrowable(() -> {
            orderService.addOrder(order);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("프로그램 일정 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("주문 등록 없는 프로그램 일정")
    @Transactional
    void add_order_not_exists_progDaily() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);
        progDailyRepository.delete(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = OrderDummy.createOrder(member2, progDaily);

        //when
        Throwable exception = catchThrowable(() -> {
            orderService.addOrder(order);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 프로그램 일정입니다.");
    }

    @Test
    @DisplayName("주문 등록 없는 정원초과")
    @Transactional
    void add_order_overcapacity() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide); //max:3
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily("202205221530", prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);
        Member member3 = MemberDummy.createMember(3);
        memberRepository.save(member3);
        Member member4 = MemberDummy.createMember(4);
        memberRepository.save(member4);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderService.addOrder(order);
        Orders order2 = OrderDummy.createOrder(member3, progDaily);
        orderService.addOrder(order2);
        Orders order3 = OrderDummy.createOrder(member4, progDaily);

        //when
        Throwable exception = catchThrowable(() -> {
            orderService.addOrder(order3);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("정원 초과입니다.");
    }

    @Test
    @DisplayName("주문 취소")
    @Transactional
    void cancel_order() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily(LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")), prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderRepository.save(order);

        //when
        orderService.cancelOrder(order);

        //then
        assertThat(order.getStatus()).isSameAs(OrderStatus.REFUNDED);
    }

    @Test
    @DisplayName("주문 취소 null 주문")
    @Transactional
    void cancel_order_null_order() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily(LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")), prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        //orderRepository.save(order);

        //when
        Throwable exception = catchThrowable(() -> {
            orderService.cancelOrder(order);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("올바르지 않은 주문 정보입니다.");
    }

    @Test
    @DisplayName("주문 취소 없는 주문")
    @Transactional
    void cancel_order_not_exists_order() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily(LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")), prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderRepository.save(order);

        orderRepository.delete(order);

        //when
        Throwable exception = catchThrowable(() -> {
            orderService.cancelOrder(order);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("존재하지 않는 주문입니다.");
    }

    @Test
    @DisplayName("주문 취소 시작 한시간 이하 전")
    @Transactional
    void cancel_order_one_hour() {
        //given
        Member member = MemberDummy.createMember(1);
        memberRepository.save(member);

        Guide guide = GuideDummy.createGuide(member);
        guideRepository.save(guide);

        Prog prog = ProgDummy.createProg(1, guide);
        progRepository.save(prog);

        ProgDaily progDaily = ProgDailyDummy.createProgDaily(LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")), prog);
        progDailyRepository.save(progDaily);

        Member member2 = MemberDummy.createMember(2);
        memberRepository.save(member2);

        Orders order = OrderDummy.createOrder(member2, progDaily);
        orderRepository.save(order);

        //when
        Throwable exception = catchThrowable(() -> {
            orderService.cancelOrder(order);
        });

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessage("환불 가능 기간이 지났습니다.");
    }
}
