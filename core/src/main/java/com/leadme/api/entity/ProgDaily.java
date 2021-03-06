package com.leadme.api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public ProgDaily(String progDate, Prog prog) {
        this.progDate = progDate;
        this.prog = prog;
    }

    public void changeProgDate(String progDate) {
        this.progDate = progDate;
    }
}
