package com.leadme.api.dto.sdto;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.GuideHashtag;
import com.leadme.api.entity.Hashtag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class GuideHashtagsDto {
    private Long guideHashtagId;
    private Long guideId;
    private Long hashtagId;
    private String name;
    private Long count;


    @QueryProjection
    public GuideHashtagsDto(Long hashtagId, Long count) {
        //this.hashtagId = hashtagId;
        this.count = count;
    }
}
