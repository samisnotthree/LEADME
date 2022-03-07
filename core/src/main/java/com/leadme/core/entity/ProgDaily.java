package com.leadme.core.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProgDaily {
    @Id
    @GeneratedValue
    private Long progDailyId;

    LocalDateTime progDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "prog_id")
    private Prog prog;

    @OneToMany(mappedBy = "progDaily")
    private List<Orders> orders = new ArrayList<>();
}
