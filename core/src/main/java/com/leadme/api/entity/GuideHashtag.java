package com.leadme.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
