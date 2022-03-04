package com.leadme.core.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Hashtag {

    @Id
    @GeneratedValue
    private Long HashtagId;

    private String name;
}
