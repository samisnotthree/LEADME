package com.leadme.api.dto.sdto;

import com.leadme.api.entity.Member;
import com.leadme.api.entity.Orders;
import com.leadme.api.entity.ProgDaily;
import com.leadme.api.entity.enums.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
public class OrderProgDailyDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private LocalDateTime payDate;
    private String status;
    
    private Long price; //주문 당사자 or 관리자만 조회
    private String payment; //주문 당사자 or 관리자만 조회

    private Long memberId;
    private String email;
    private String name;
    private String phone;

    private Long progDailyId;
    private String progDate;
    
    private Long progId;
    private String progName;
    
    private Long guideId;
    private String guideName;
    

    @QueryProjection
    public OrderProgDailyDto(Long orderId, LocalDateTime orderDate, LocalDateTime payDate, String status, Long memberId, String email, String name, String phone, Long progDailyId, String progDate, String progId, String progName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.payDate = payDate;
        this.status = status;
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.progDailyId = progDailyId;
        this.progDate = progDate;
        this.progId = progId;
        this.progName = progName;
    }
    
    @QueryProjection
    public OrderProgDailyDto(Long orderId, LocalDateTime orderDate, LocalDateTime payDate, String status, Long price, String payment, Long memberId, String email, String name, String phone, Long progDailyId, String progDate, Long progId, String progName, Long guideId, String guideName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.payDate = payDate;
        this.status = status;
        this.price = price;
        this.payment = payment;
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.progDailyId = progDailyId;
        this.progDate = progDate;
        this.progId = progId;
        this.progName = progName;
        this.guideId = guideId;
        this.guideName = guideName;
    }
}
