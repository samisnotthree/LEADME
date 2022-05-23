package com.leadme.api.dto;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@ToString
public class MemberDto {
    private Long memberId;

    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;

    private String name;
    private String pass;
    private String phone;
    private LocalDateTime inDate;
    private LocalDateTime outDate;
    private Guide guide;

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
