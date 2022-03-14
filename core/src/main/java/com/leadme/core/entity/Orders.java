package com.leadme.core.entity;

import com.leadme.core.entity.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {
    @Id
    @GeneratedValue
    private Long orderId;
    private Long price;
    private String payment;
    private LocalDateTime orderDate;
    private LocalDateTime payDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "prog_daily_id")
    private ProgDaily progDaily;
    
    public Orders(Long price, String payment, LocalDateTime orderDate, LocalDateTime payDate, OrderStatus status, Member member, ProgDaily progDaily) {
        this.price = price;
        this.payment = payment;
        this.orderDate = orderDate;
        this.payDate = payDate;
        this.status = status;
        this.member = member;
        this.progDaily = progDaily;
    }

    public void successPayed() {
        this.orderDate = LocalDateTime.now();
        this.payDate = LocalDateTime.now();
        this.status = OrderStatus.PAYED;
    }

}
