package com.leadme.core.entity;

import javax.persistence.*;

@Entity
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private Guide guide;
}
