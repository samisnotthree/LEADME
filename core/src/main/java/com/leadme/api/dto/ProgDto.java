package com.leadme.api.dto;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Prog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class ProgDto {
    private Long progId;
    private String name;
    private String desc;
    private Integer maxMember;
    private String duration;
    private Long price;
    private String meetLocation;
    private LocalDateTime inDate;
    private LocalDateTime outDate;
    private Guide guide;

    @Builder
    public ProgDto(String name, String desc, Integer maxMember, String duration, Long price, String meetLocation, LocalDateTime inDate, LocalDateTime outDate, Guide guide) {
        this.name = name;
        this.desc = desc;
        this.maxMember = maxMember;
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
                .maxMember(maxMember)
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
        this.maxMember = prog.getMaxMember();
        this.duration = prog.getDuration();
        this.price = prog.getPrice();
        this.meetLocation = prog.getMeetLocation();
        this.inDate = prog.getInDate();
        this.outDate = prog.getOutDate();
        this.guide = prog.getGuide();
    }
}
