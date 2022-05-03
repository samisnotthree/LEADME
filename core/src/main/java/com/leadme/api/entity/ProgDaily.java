package com.leadme.api.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgDaily {
    @Id
    @GeneratedValue
    private Long progDailyId;

    private LocalDateTime progDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "prog_id")
    private Prog prog;

    @OneToMany(mappedBy = "progDaily")
    private List<Orders> orders = new ArrayList<>();

    @Builder
    public ProgDaily(LocalDateTime progDate, Prog prog) {
        this.progDate = progDate;
        this.prog = prog;
    }
}
