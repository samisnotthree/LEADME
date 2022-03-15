package com.leadme.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class GuideHashtag {

    @Id
    @GeneratedValue
    private Long guideHashtagId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private Guide guide;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @Builder
    public GuideHashtag(Guide guide, Hashtag hashtag) {
        this.guide = guide;
        this.hashtag = hashtag;
    }
}
