package com.leadme.dummy;

import com.leadme.api.entity.Member;

import java.time.LocalDateTime;

public class MemberDummy {

    public static Member createMember(int i) {
        return Member.builder()
                .name("김멤버" + i)
                .email("member-kim" + i + "@gmail.com")
                .pass("temp-pass")
                .phone("01012345678")
                .inDate(LocalDateTime.now())
                .build();
    }
}
