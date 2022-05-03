package com.leadme.api.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public void changeInDate(LocalDateTime dateTime) {
        this.inDate = dateTime;
    }

    public void changeOutDate(LocalDateTime dateTime) {
        this.outDate = dateTime;
    }
}
