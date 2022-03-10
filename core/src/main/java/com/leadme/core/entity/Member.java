package com.leadme.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long memberId;

    private String email;
    private String name;
    private String pass;
    private String phone;
    private String photo;
    @Setter
    private LocalDateTime inDate;
    @Setter
    private LocalDateTime outDate;

    @OneToOne(mappedBy = "member")
    private Guide guide;

    public void changeGuide(Guide guide) {
        this.guide = guide;
        guide.setMember(this);
    }
}
