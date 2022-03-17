package com.leadme.core.dto;

import com.leadme.core.entity.Guide;
import com.leadme.core.entity.Prog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProgDto {
    private Long progId;
    private String name;
    private String desc;
    private Integer maxProg;
    private String duration;
    private Long price;
    private String meetLocation;
    private LocalDateTime inDate;
    private LocalDateTime outDate;
    private Guide guide;

    @Builder
    public ProgDto(String name, String desc, Integer maxProg, String duration, Long price, String meetLocation, LocalDateTime inDate, LocalDateTime outDate, Guide guide) {
        this.name = name;
        this.desc = desc;
        this.maxProg = maxProg;
        this.duration = duration;
        this.price = price;
        this.meetLocation = meetLocation;
        this.inDate = inDate;
        this.outDate = outDate;
        this.guide = guide;
    }

    public Prog toEntity() {
        return Prog.builder()
                .name(name)
                .desc(desc)
                .maxProg(maxProg)
                .duration(duration)
                .price(price)
                .meetLocation(meetLocation)
                .inDate(inDate)
                .outDate(outDate)
                .guide(guide)
                .build();
    }

    //entity -> dto
    public ProgDto(Prog prog) {
        this.progId = prog.getProgId();
        this.name = prog.getName();
        this.desc = prog.getDesc();
        this.maxProg = prog.getMaxProg();
        this.duration = prog.getDuration();
        this.price = prog.getPrice();
        this.meetLocation = prog.getMeetLocation();
        this.inDate = prog.getInDate();
        this.outDate = prog.getOutDate();
        this.guide = prog.getGuide();
    }
}
