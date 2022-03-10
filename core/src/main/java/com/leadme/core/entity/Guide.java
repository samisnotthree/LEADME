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
    @Setter
    private Long guideId;

    @Setter
    private String desc;
    @Setter
    private LocalDateTime inDate;
    @Setter
    private LocalDateTime outDate;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
