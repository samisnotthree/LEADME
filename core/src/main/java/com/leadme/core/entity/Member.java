package com.leadme.core.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long memberId;

    private String email;
    private String name;
    private String pass;
    private String phone;
    private String photo;
    private LocalDateTime inDate;
    private LocalDateTime outDate;

    @OneToOne(mappedBy = "member")
    private Guide guide;

    @Builder
    public Member(String email, String name, String pass, String phone, String photo, LocalDateTime inDate, LocalDateTime outDate, Guide guide) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.phone = phone;
        this.photo = photo;
        this.inDate = inDate;
        this.outDate = outDate;
        this.guide = guide;
    }

    public void nowInDate() {
        this.inDate = LocalDateTime.now();
    }

    public void nowOutDate() {
        this.outDate = LocalDateTime.now();
    }

//    public void changeGuide(Guide guide) {
//        this.guide = guide;
//        guide.setMember(this);
//    }
}
