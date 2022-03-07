package com.leadme.core.entity;

import com.leadme.core.entity.orders.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Orders {
    @Id
    @GeneratedValue
    private Long orderId;
    private Long price;
    private Status status;
    private String payment;
    private LocalDateTime orderDate;
    private LocalDateTime payDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "prog_daily_id")
    private ProgDaily progDaily;
}
