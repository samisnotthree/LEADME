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
    private LocalDateTime inDate;
    private LocalDateTime outDate;

    @OneToOne(mappedBy = "member")
    private Guide guide;

    @Builder
    public Member(String email, String name, String pass, String phone, LocalDateTime inDate, LocalDateTime outDate, Guide guide) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.phone = phone;
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

    public void changeMemberInfo(Member member) {
        this.name = member.getName();
        this.phone = member.getPhone();
        this.pass = member.getPass();
    }
}
