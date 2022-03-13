package com.leadme.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Prog {
    @Id
    @GeneratedValue
    private Long progId;

    private String name;
    private String desc;
    private Integer maxMember;
    private String duration;
    private Long price;
    private String prepMat;
    private String meetLocation;
    private LocalDateTime inDate;

    private LocalDateTime outDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private Guide guide;

}
