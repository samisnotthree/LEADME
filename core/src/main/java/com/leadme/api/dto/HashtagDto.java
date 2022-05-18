package com.leadme.api.dto;

import com.leadme.api.dto.sdto.ProgHashtagsDto;
import com.leadme.api.entity.Hashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagDto {
    private Long hashtagId;
    private String name;
    
    @Builder
    public HashtagDto(String name) {
        this.name = name;
    }

    public Hashtag toEntity() {
        return Hashtag.builder()
          .name(name)
          .build();
    }
    
    public HashtagDto(Hashtag hashtag) {
        this.hashtagId = hashtag.getHashtagId();
        this.name = hashtag.getName();
    }
}
