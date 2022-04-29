package com.leadme.core.controller;

import com.leadme.core.dto.GuideDto;
import com.leadme.core.entity.Guide;
import com.leadme.core.repository.GuideRepository;
import com.leadme.core.service.GuideService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guides")
public class GuideController {
    private final GuideService guideService;
    private final GuideRepository guideRepository;

    @Transactional
    @PostMapping
    public Long joinGuide(@RequestBody GuideDto guideDto) {
        return guideService.joinGuide(guideDto.getMemberId(), guideDto.getDesc()).getGuideId();
    }

    @GetMapping("/{id}")
    public Result findGuide(@PathVariable("id") Long guideId) {
        return new Result(guideRepository.findById(guideId)
                .stream()
                .map(GuideDto::new)
                .collect(Collectors.toList()));
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T members;
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteGuide(@PathVariable("id") Long guideId) {
        guideService.deleteGuide(guideId);
    }
}
