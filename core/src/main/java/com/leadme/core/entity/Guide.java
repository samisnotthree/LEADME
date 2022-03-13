package com.leadme.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
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

}
