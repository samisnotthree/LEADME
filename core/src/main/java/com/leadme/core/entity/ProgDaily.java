package com.leadme.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class ProgDaily {
    @Id
    @GeneratedValue
    private Long progDailyId;

    private String progDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "prog_id")
    private Prog prog;

    @OneToMany(mappedBy = "progDaily")
    private List<Orders> orders = new ArrayList<>();
}
