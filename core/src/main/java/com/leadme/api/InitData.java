package com.leadme.api;

import com.leadme.api.dummy.*;
import com.leadme.api.entity.*;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.guideHashtag.GuideHashtagRepository;
import com.leadme.api.repository.hashtag.HashtagRepository;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.order.OrderRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import com.leadme.api.repository.progHashtag.ProgHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Profile("local")
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
        @Autowired
        HashtagRepository hashtagRepository;
        @Autowired
        ProgHashtagRepository progHashtagRepository;
        @Autowired
        GuideHashtagRepository guideHashtagRepository;

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

            Hashtag hashtag = HashtagDummy.createHashtag("서울");
            hashtagRepository.save(hashtag);

            Hashtag hashtag2 = HashtagDummy.createHashtag("자전거");
            hashtagRepository.save(hashtag2);

            ProgHashtag progHashtag = HashtagDummy.createProgHashtag(prog, hashtag);
            progHashtagRepository.save(progHashtag);

            ProgHashtag progHashtag2 = HashtagDummy.createProgHashtag(prog, hashtag2);
            progHashtagRepository.save(progHashtag2);

            ProgHashtag progHashtag3 = HashtagDummy.createProgHashtag(prog2, hashtag);
            progHashtagRepository.save(progHashtag3);

            GuideHashtag guideHashtag = HashtagDummy.createGuideHashtag(guide, hashtag);
            guideHashtagRepository.save(guideHashtag);
        }
    }
}
