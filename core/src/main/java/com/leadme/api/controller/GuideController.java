package com.leadme.api.controller;

import com.leadme.api.dto.GuideDto;
import com.leadme.api.dto.GuideHashtagDto;
import com.leadme.api.dto.ProgHashtagDto;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.service.GuideHashtagService;
import com.leadme.api.service.GuideService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GuideController {
    private final GuideService guideService;
    private final GuideRepository guideRepository;
    private final GuideHashtagService guideHashtagService;

    @Transactional
    @PostMapping("/guides")
    public Long joinGuide(@RequestBody GuideDto guideDto) {
        return guideService.joinGuide(guideDto.getMember().getMemberId(), guideDto.getDesc()).getGuideId();
    }

    @GetMapping("/guides/{id}")
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
    @DeleteMapping("/guides/{id}")
    public void deleteGuide(@PathVariable("id") Long guideId) {
        guideService.deleteGuide(guideId);
    }

    /**
     *  가이드 해시태그 등록
     */
    @Transactional
    @PostMapping("/guide-hashtags")
    public ResponseEntity<Long> addGuideHashtag(@RequestBody GuideHashtagDto guideHashtagDto) {
        return ResponseEntity.ok(guideHashtagService.addGuideHashtag(guideHashtagDto.getGuide().getGuideId(), guideHashtagDto.getHashtag().getHashtagId()).getGuideHashtagId());
    }
}
