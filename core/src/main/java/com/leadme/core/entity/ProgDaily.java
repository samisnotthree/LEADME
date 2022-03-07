package com.leadme.core.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProgDaily {
    @Id
    @GeneratedValue
    private Long progDailyId;

    LocalDateTime progDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "prog_id")
    private ProgMain progMain;
}
