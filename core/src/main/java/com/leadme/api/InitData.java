package com.leadme.api;

import com.leadme.api.dummy.*;
import com.leadme.api.entity.*;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.order.OrderRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    static class InitService {
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

        @Transactional
        public void init() {
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

            Orders order = OrderDummy.createOrder(member, progDaily);
            orderRepository.save(order);

            Orders order2 = OrderDummy.createOrder(member, progDaily);
            orderRepository.save(order2);
        }
    }
}
