package com.leadme.api.dto;

import com.leadme.api.entity.Hashtag;
import com.leadme.api.entity.Guide;
import com.leadme.api.entity.GuideHashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class GuideHashtagDto {
    private Long guideHashtagId;
    private Guide guide;
    private Hashtag hashtag;

    @Builder
    public GuideHashtagDto(Guide guide, Hashtag hashtag) {
        this.guide = guide;
        this.hashtag = hashtag;
    }

    public GuideHashtag toEntity() {
        return GuideHashtag.builder()
                .guide(guide)
                .hashtag(hashtag)
                .build();
    }

    //entity -> dto
    public GuideHashtagDto(GuideHashtag guideHashtag) {
        this.guideHashtagId = guideHashtag.getGuideHashtagId();
        this.guide = guideHashtag.getGuide();
        this.hashtag = guideHashtag.getHashtag();
    }
}
