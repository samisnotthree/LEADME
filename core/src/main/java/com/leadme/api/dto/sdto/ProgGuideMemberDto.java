package com.leadme.api.dto.sdto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class ProgGuideMemberDto {
    private Long progId;
    private String name;
    private String desc;
    private Integer maxMember;
    private String duration;
    private Long price;
    private String meetLocation;
    private Long guideId;
    private String guideDesc;
    private Long memberId;
    private String memberName;

    @QueryProjection
    public ProgGuideMemberDto(Long progId, String name, String desc, Integer maxMember, String duration, Long price, String meetLocation, Long guideId, String guideDesc, Long memberId, String memberName) {
        this.progId = progId;
        this.name = name;
        this.desc = desc;
        this.maxMember = maxMember;
        this.duration = duration;
        this.price = price;
        this.meetLocation = meetLocation;
        this.guideId = guideId;
        this.guideDesc = guideDesc;
        this.memberId = memberId;
        this.memberName = memberName;
    }
}
