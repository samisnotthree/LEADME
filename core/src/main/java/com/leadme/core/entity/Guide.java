package com.leadme.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Guide {

    @Id
    @GeneratedValue
    private Long guideId;

    private String desc;
    private LocalDateTime inDate;
    @Setter
    private LocalDateTime outDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
