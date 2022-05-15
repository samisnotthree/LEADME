package com.leadme.api.dto;

import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgDaily;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProgDailyDto {
    private Long progDailyId;
    private LocalDateTime progDate;
    private Prog prog;
    private Long progId;
  
    @Builder
    public ProgDailyDto(LocalDateTime progDate, Prog prog) {
        this.progDate = progDate;
        this.prog = prog;
    }
  
    public ProgDaily toEntity() {
        return ProgDaily.builder()
          .progDate(progDate)
          .prog(prog)
          .build();
    }
  
    public ProgDailyDto(ProgDaily progDaily) {
        this.progDate = progDaily.getProgDate();
        this.prog = progDaily.getProg();
    }

    @QueryProjection
    public ProgDailyDto(Long progDailyId, LocalDateTime progDate, Long progId) {
        this.progDailyId = progDailyId;
        this.progDate = progDate;
        this.progId = progId;
    }
}
