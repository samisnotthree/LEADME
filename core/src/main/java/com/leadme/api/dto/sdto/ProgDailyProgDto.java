package com.leadme.api.dto.sdto;

import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgDaily;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProgDailyProgDto {
    private Long progDailyId;
    private String progDate;
    private Long progId;


    @QueryProjection
    public ProgDailyProgDto(Long progDailyId, String progDate, Long progId) {
        this.progDailyId = progDailyId;
        this.progDate = progDate;
        this.progId = progId;
    }
}
