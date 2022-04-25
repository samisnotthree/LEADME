package com.leadme.core.dto;

import com.leadme.core.entity.Hashtag;
import com.leadme.core.entity.Prog;
import com.leadme.core.entity.ProgHashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ProgHashtagDto {
    private Long progHashtagId;
    private Prog prog;
    private Hashtag hashtag;

    @Builder
    public ProgHashtagDto(Prog prog, Hashtag hashtag) {
        this.prog = prog;
        this.hashtag = hashtag;
    }

    public ProgHashtag toEntity() {
        return ProgHashtag.builder()
                .prog(prog)
                .hashtag(hashtag)
                .build();
    }

    //entity -> dto
    public ProgHashtagDto(ProgHashtag progHashtag) {
        this.progHashtagId = progHashtag.getProgHashtagId();
        this.prog = progHashtag.getProg();
        this.hashtag = progHashtag.getHashtag();
    }
}
