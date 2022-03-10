package com.leadme.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Hashtag {

    @Id
    @GeneratedValue
    private Long HashtagId;

    private String name;

}
