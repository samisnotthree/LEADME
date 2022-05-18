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

    private Long memberId;
    private String email;
    private String name;
    private String phone;

    private Long progDailyId;
    private String progDate;

    @QueryProjection
    public OrderProgDailyDto(Long orderId, LocalDateTime orderDate, LocalDateTime payDate, String status, Long memberId, String email, String name, String phone, Long progDailyId, String progDate) {
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
    }
}
