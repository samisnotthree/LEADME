package com.leadme.core.dto;

import com.leadme.core.entity.Guide;
import com.leadme.core.entity.Prog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProgDto {
    private Long progId;
    private String name;
    private String desc;
    private Integer maxProg;
    private String duration;
    private Long price;
    private String prepMat;
    private String meetLocation;
    private LocalDateTime inDate;
    private LocalDateTime outDate;
    private Guide guide;

    @Builder
    public ProgDto(String email, String name, String pass, String phone, String photo, LocalDateTime inDate, LocalDateTime outDate, Guide guide) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.phone = phone;
        this.photo = photo;
        this.inDate = inDate;
        this.outDate = outDate;
        this.guide = guide;
    }

    public Prog toEntity() {
        return Prog.builder()
                .email(email)
                .name(name)
                .pass(pass)
                .phone(phone)
                .photo(photo)
                .inDate(inDate)
                .outDate(outDate)
                .guide(guide)
                .build();
    }

    //entity -> dto
    public ProgDto(Prog prog) {
        this.progId = prog.getProgId();
        this.email = prog.getEmail();
        this.name = prog.getName();
        this.pass = prog.getPass();
        this.phone = prog.getPhone();
        this.photo = prog.getPhoto();
        this.inDate = prog.getInDate();
        this.outDate = prog.getOutDate();
        this.guide = prog.getGuide();
    }
}
