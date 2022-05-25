package com.leadme.api.dummy;

import com.leadme.api.entity.*;

public class HashtagDummy {

    public static Hashtag createHashtag(String name) {
        return Hashtag.builder()
                .name(name)
                .build();
    }

    public static ProgHashtag createProgHashtag(Prog prog, Hashtag hashtag) {
        return ProgHashtag.builder()
                .prog(prog)
                .hashtag(hashtag)
                .build();
    }

    public static GuideHashtag createGuideHashtag(Guide guide, Hashtag hashtag) {
        return GuideHashtag.builder()
                .guide(guide)
                .hashtag(hashtag)
                .build();
    }
}

