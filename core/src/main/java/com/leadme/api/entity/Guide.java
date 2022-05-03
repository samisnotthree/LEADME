package com.leadme.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guide {

    @Id
    @GeneratedValue
    private Long guideId;

    private String desc;
    private LocalDateTime inDate;
    private LocalDateTime outDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Guide(String desc, LocalDateTime inDate, LocalDateTime outDate, Member member) {
        this.desc = desc;
        this.inDate = inDate;
        this.outDate = outDate;
        this.member = member;
    }

    public void changeOutDate(LocalDateTime dateTime) {
        this.outDate = dateTime;
    }

//    public void changeGuide(String desc, LocalDateTime inDate, LocalDateTime outDate, Member member) {
//        this.desc = desc;
//        this.inDate = inDate;
//        this.outDate = outDate;
//        this.member = member;
//    }
}
