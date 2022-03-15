package com.leadme.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    
    @Builder
    public Hashtag(String name) {
        this.name = name;
    }

    public void changeHashtag(String name) {
        this.name = name;
    }
}
