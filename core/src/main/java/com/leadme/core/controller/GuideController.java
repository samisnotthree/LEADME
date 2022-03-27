package com.leadme.core.controller;

import com.leadme.core.dto.GuideDto;
import com.leadme.core.entity.Guide;
import com.leadme.core.repository.GuideRepository;
import com.leadme.core.service.GuideService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GuideController {
    private final GuideService guideService;
    private final GuideRepository guideRepository;

    @Transactional
    @PostMapping("/guide")
    public Long joinGuide(@RequestBody Long memberId, @RequestBody String desc) {
        return guideService.joinGuide(memberId, desc).getGuideId();
    }

    @GetMapping("guide")
    public Result findGuide(Guide guide) {
        return new Result(guideRepository.findById(guide.getGuideId())
                .stream()
                .map(GuideDto::new)
                .collect(Collectors.toList()));
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T members;
    }
}
