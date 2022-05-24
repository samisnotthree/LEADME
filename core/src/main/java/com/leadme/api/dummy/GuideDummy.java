package com.leadme.api.dummy;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;

import java.time.LocalDateTime;

public class GuideDummy {

    public static Guide createGuide(Member member) {
        return Guide.builder()
                .desc("안녕하세요. 서울 전문 가이드 김멤버입니다.")
                .inDate(LocalDateTime.now())
                .member(member)
                .build();
    }
}
