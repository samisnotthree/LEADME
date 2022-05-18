package com.leadme.api.controller;

import com.leadme.api.dto.HashtagDto;
import com.leadme.api.dto.ProgHashtagDto;
import com.leadme.api.dto.sdto.ProgHashtagsDto;
import com.leadme.api.repository.hashtag.HashtagQueryRepository;
import com.leadme.api.repository.hashtag.HashtagRepository;
import com.leadme.api.service.HashtagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;
    private final HashtagRepository hashtagRepository;
    private final HashtagQueryRepository hashtagQueryRepository;
  
    @Transactional
    @PostMapping("/hashtags")
    public Long addHashtag(@RequestBody HashtagDto hashtagDto) {
        return hashtagService.addHashtag(hashtagDto.toEntity());
    }
    
    @GetMapping("hashtags")
    public Result findAll() {
        return new Result(hashtagRepository.findAll()
                .stream()
                .map(HashtagDto::new)
                .collect(Collectors.toList()));
    }

    /**
     *  프로그램에서의 사용 횟수 top 10인 해시태그 조회
     */
    @GetMapping("popular-hashtags")
    public Result findPopularHashtags() {
        return new Result(hashtagQueryRepository.searchHashtagsWithCount()
                .stream()
                .sorted(Comparator.comparing(ProgHashtagsDto::getCount).reversed())
                .limit(10)
                .map(ProgHashtagsDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("hashtags/{name}")
    public Result findByName(@PathVariable("name") String name) {
        return new Result(hashtagRepository.findByNameContains(name)
                .stream()
                .map(HashtagDto::new)
                .collect(Collectors.toList()));
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T hashtags;
    }
  
    @Transactional
    @DeleteMapping("/hashtags/{id}")
    public void deleteHashtag(@PathVariable("id") Long hashtagId) {
        hashtagService.deleteHashtag(hashtagId);
    }
}
