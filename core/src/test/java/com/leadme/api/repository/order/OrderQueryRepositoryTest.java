package com.leadme.api.repository.order;

import com.leadme.api.dto.condition.OrderSearchCondition;
import com.leadme.api.dto.sdto.OrderProgDailyDto;
import com.leadme.api.entity.*;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import com.leadme.api.service.*;
import com.leadme.dummy.*;
import org.assertj.core.api.Assertions;
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
    @Autowired
    MemberService memberService;
    @Autowired
    GuideService guideService;
    @Autowired
    ProgService progService;
    @Autowired
    ProgDailyService progDailyService;
    @Autowired
    OrderService orderService;

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
        Assertions.assertThat(orders.getContent().size()).isEqualTo(1);
    }
}
