package com.leadme.api.dummy;

import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Prog;

public class ProgDummy {
    public static Prog createProg(int i, Guide guide) {
        return Prog.builder()
                .name("경복궁과 광화문광장" + i)
                .desc("경복궁과 광화문광장을 주제로 투어합니다.")
                .maxMember(10)
                .duration("대략 한시간 반")
                .price(20000L)
                .meetLocation("광화문 앞")
                .guide(guide)
                .build();
    }
}
