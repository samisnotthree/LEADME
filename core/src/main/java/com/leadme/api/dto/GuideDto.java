package com.leadme.api.dto;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GuideDto {
    private Long guideId;
    private String desc;
    private LocalDateTime inDate;
    private LocalDateTime outDate;
    private Member member;
    //private Long memberId;
  
    @Builder
    public GuideDto(String desc, LocalDateTime inDate, LocalDateTime outDate, Member member) {
        this.desc = desc;
        this.inDate = inDate;
        this.outDate = outDate;
        this.member = member;
    }
    
    public Guide toEntity() {
        return Guide.builder()
          .desc(desc)
          .inDate(inDate)
          .outDate(outDate)
          .member(member)
          .build();
    }

    public GuideDto(Guide guide) {
        this.guideId = guide.getGuideId();
        this.desc = guide.getDesc();
        this.inDate = guide.getInDate();
        this.outDate = guide.getOutDate();
        this.member = guide.getMember();
    }
}
