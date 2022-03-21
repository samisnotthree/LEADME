package com.leadme.core.controller;

import com.leadme.core.repository.GuideRepository;
import com.leadme.core.service.GuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GuideController {
    private final GuideService guideService;
    private final GuideRepository guideRepository;

    @Transactional
    @PostMapping("/joinGuide")
    public Long joinGuide(@RequestBody Long memberId, @RequestBody String desc) {
        return guideService.joinGuide(memberId, desc).getGuideId();
    }
}
