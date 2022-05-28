package com.leadme.api.service;

import com.leadme.api.entity.Orders;
import com.leadme.api.entity.enums.OrderStatus;
import com.leadme.api.repository.member.MemberRepository;
import com.leadme.api.repository.order.OrderRepository;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProgDailyRepository progDailyRepository;

    @Transactional
    public Long addOrder(Orders order) {
        Optional.ofNullable(order.getMember()).orElseThrow(() -> new IllegalStateException("사용자 정보가 올바르지 않습니다."));
        Optional.ofNullable(order.getProgDaily()).orElseThrow(() -> new IllegalStateException("프로그램 일정 정보가 올바르지 않습니다."));
        memberRepository.findById(order.getMember().getMemberId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));
        progDailyRepository.findById(order.getProgDaily().getProgDailyId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 프로그램 일정입니다."));

        if (!validateOrder(order)) {
            throw new IllegalStateException("정원 초과입니다.");
        }
        //TODO 결제 분리 시 결제 일시 로직 수정
        order.successPayed();
        return orderRepository.save(order).getOrderId();
    }

    public boolean validateOrder(Orders order) {
        List<Orders> foundOrders = orderRepository.findByProgDaily(order.getProgDaily());
        if (foundOrders.isEmpty()) {
            return true;
        }
        return foundOrders.size() < foundOrders.get(0).getProgDaily().getProg().getMaxMember();
    }
    
    @Transactional
    public void cancelOrder(Orders order) {
        Optional.ofNullable(order.getOrderId()).orElseThrow(() -> new IllegalStateException("올바르지 않은 주문 정보입니다."));

        if (!canCancelOrder(order)) {
            throw new IllegalStateException("환불 가능 기간이 지났습니다.");
        }

        Optional<Orders> orders = orderRepository.findById(order.getOrderId());
        orders.ifPresentOrElse(
                o -> o.changeStatus(OrderStatus.REFUNDED),
                () -> orders.orElseThrow(() -> new IllegalStateException("존재하지 않는 주문입니다."))
        );
    }
    
    /**
    * 프로그램 시작 한시간 전까지 취소 가능
    */
    public boolean canCancelOrder(Orders order) {
        return Long.parseLong(order.getProgDaily().getProgDate()) > Long.parseLong(LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
    }
}
