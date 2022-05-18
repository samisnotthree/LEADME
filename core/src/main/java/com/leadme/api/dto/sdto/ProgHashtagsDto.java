package com.leadme.api.dto.sdto;

import com.leadme.api.entity.Hashtag;
import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgHashtag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ProgHashtagsDto {
    private Long hashtagId;
    private String name;
    private Long count;

    @QueryProjection
    public ProgHashtagsDto(Long hashtagId, String name, Long count) {
        this.hashtagId = hashtagId;
        this.name = name;
        this.count = count;
    }

    public ProgHashtagsDto(ProgHashtagsDto progHashtagsDto) {
        this.hashtagId = progHashtagsDto.getHashtagId();
        this.name = progHashtagsDto.getName();
    }
}
