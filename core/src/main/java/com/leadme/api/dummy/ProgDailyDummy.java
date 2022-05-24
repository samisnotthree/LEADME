package com.leadme.api.dummy;

import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgDaily;

public class ProgDailyDummy {

    public static ProgDaily createProgDaily(String progDate, Prog prog) {
        return ProgDaily.builder()
                .progDate(progDate)
                .prog(prog)
                .build();
    }
}
