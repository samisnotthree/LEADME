package com.leadme.core.entity;

import com.leadme.core.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
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
}
