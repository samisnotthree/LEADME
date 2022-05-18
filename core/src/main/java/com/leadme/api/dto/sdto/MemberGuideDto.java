package com.leadme.api.dto.sdto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberGuideDto {
    private Long memberId;
    private String email;
    private String name;
    private String phone;
    private Long guideId;
    private String desc;

    @QueryProjection
    public MemberGuideDto(Long memberId, String email, String name, String phone, Long guideId, String desc) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.guideId = guideId;
        this.desc = desc;
    }
}
