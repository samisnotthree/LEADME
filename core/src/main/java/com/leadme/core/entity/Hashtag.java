package com.leadme.core.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Hashtag {

    @Id
    @GeneratedValue
    private Long HashtagId;

    private String name;

    @OneToMany(mappedBy = "hashTag")
    private List<GuideHashtag> guideHashtag = new ArrayList<>();
}
