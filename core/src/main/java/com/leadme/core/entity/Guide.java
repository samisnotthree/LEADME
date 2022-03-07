package com.leadme.core.entity;

import lombok.Getter;

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
    private LocalDateTime outDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "guide")
    private List<GuideHashtag> guideHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "guide")
    private List<Prog> progs = new ArrayList<>();
}
