package com.leadme.api.dto;

import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgDaily;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProgDailyDto {
    private Long progDailyId;
    private LocalDateTime progDate;
    private Prog prog;
  
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
}
