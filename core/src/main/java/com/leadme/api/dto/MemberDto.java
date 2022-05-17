package com.leadme.api.dto;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberDto {
    private Long memberId;
    private String email;
    private String name;
    private String pass;
    private String phone;
    private LocalDateTime inDate;
    private LocalDateTime outDate;
    private Guide guide;
    //private Long guideId;
    //private String desc;

    @QueryProjection
    public MemberDto(Long memberId, String email, String name, String pass, String phone, LocalDateTime inDate, LocalDateTime outDate, Long guideId, String desc) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.phone = phone;
        this.inDate = inDate;
        this.outDate = outDate;
        //this.guideId = guideId;
        //this.desc = desc;
    }

    @Builder
    public MemberDto(String email, String name, String pass, String phone, LocalDateTime inDate, LocalDateTime outDate, Guide guide) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.phone = phone;
        this.inDate = inDate;
        this.outDate = outDate;
        this.guide = guide;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .pass(pass)
                .phone(phone)
                .inDate(inDate)
                .outDate(outDate)
                .guide(guide)
                .build();
    }

    //entity -> dto
    public MemberDto(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.pass = member.getPass();
        this.phone = member.getPhone();
        this.inDate = member.getInDate();
        this.outDate = member.getOutDate();
        this.guide = member.getGuide();
    }

//    public void changeGuide(Guide guide) {
//        this.guide = guide;
//        guide.setMember(this);
//    }
}
