package com.leadme.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    private Long memberId;

    private String email;
    private String name;
    private String pass;
    private String phone;
    private String photo;
    private LocalDateTime inDate;
    private LocalDateTime outDate;

}
