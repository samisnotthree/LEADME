package com.leadme.api.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Prog {
    @Id
    @GeneratedValue
    private Long progId;

    private String name;
    private String desc;
    private Integer maxMember;
    private String duration;
    private Long price;
    private String meetLocation;
    private LocalDateTime inDate;

    @Setter
    private LocalDateTime outDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private Guide guide;

    @Builder
    public Prog(String name, String desc, Integer maxMember, String duration, Long price, String meetLocation, LocalDateTime inDate, LocalDateTime outDate, Guide guide) {
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

    public void changeProgInfo(Prog prog) {
        this.name = prog.getName();
        this.desc = prog.getDesc();
        this.maxMember = prog.getMaxMember();
        this.duration = prog.getDuration();
        this.price = prog.getPrice();
        this.meetLocation = prog.getMeetLocation();
    }
}
