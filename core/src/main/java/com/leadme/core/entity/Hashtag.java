package com.leadme.core.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
