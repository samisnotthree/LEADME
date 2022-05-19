package com.leadme.api.dto.condition;

import lombok.Data;

@Data
public class OrderSearchCondition {
    private Long progDailyId;
    private Long memberId;
    private String name;
    private String email;
    private String progName;
}
