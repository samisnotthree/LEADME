package com.leadme.api.repository.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderQueryRepositoryTest {

    @Test
    @DisplayName("프로그램 일시별 주문내역 조회(가이드용)")
    void searchOrdersByProgDailyId() {
        Member member = Member.builder()
                .name("김멤버")
                .email("member-kim@gmail.com")
                .pass("temp-pass")
                .phone("01012345678")
                .build();
        Long joinedMemberId = memberService.joinMember(member);

        Guide guide = guideService.joinGuide(joinedMemberId, "안녕하세요. 서울 전문 가이드 김멤버입니다.");

        Prog prog = Prog.builder()
                .name("경복궁과 광화문광장")
                .desc("경복궁과 광화문광장을 주제로 투어합니다.")
                .maxMember(10)
                .duration("대략 한시간 반")
                .price(20000)
                .meetLocation("광화문 앞")
                .guide(guide)
                .build();
        progService.addProg(prog);

        Prog prog2 = Prog.builder()
                .name("name22")
                .desc("desc22")
                .guide(guide)
                .build();
        progService.addProg(prog2);
        
        ProgDaily progDaily = ProgDaily.builder()
                .progDate("202205221530")
                .prog(prog)
                .build();
        progDailyService.addProgDaily(progDaily);
        
        Member member2 = Member.builder()
                .name("김고객")
                .email("client-kim@gmail.com")
                .pass("temp-pass")
                .phone("01012345678")
                .build();
        Long joinedMemberId = memberService.joinMember(member2);

        Orders order = Orders.builder()
                .price(18000)
                .status(OrderStatus.PAYED)
                .payment("네이버페이")
                .orderDate(LocalDateTime.now())
                .payDate(LocalDateTime.now())
                .member(member2)
                .progDaily(progDaily)
                .build();
        orderService.addOrder(order);
    }
}
