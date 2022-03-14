package com.leadme.core.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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

    public Guide(String desc, LocalDateTime inDate, LocalDateTime outDate, Member member) {
        this.desc = desc;
        this.inDate = inDate;
        this.outDate = outDate;
        this.member = member;
    }

    public void nowOutDate() {
        this.outDate = LocalDateTime.now();
    }

//    public void changeGuide(String desc, LocalDateTime inDate, LocalDateTime outDate, Member member) {
//        this.desc = desc;
//        this.inDate = inDate;
//        this.outDate = outDate;
//        this.member = member;
//    }
}
