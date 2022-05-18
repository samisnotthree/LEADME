package com.leadme.api.dto;

import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgDaily;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProgDailyDto {
    private Long progDailyId;
    private String progDate;
    private Prog prog;

    @Builder
    public ProgDailyDto(String progDate, Prog prog) {
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
