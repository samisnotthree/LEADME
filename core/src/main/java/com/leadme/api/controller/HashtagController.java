package com.leadme.api.controller;

import com.leadme.api.dto.HashtagDto;
import com.leadme.api.repository.hashtag.HashtagRepository;
import com.leadme.api.service.HashtagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;
    private final HashtagRepository hashtagRepository;
  
    @Transactional
    @PostMapping("/hashtags")
    public Long addHashtag(@RequestBody HashtagDto hashtagDto) {
        return hashtagService.addHashtag(hashtagDto.toEntity());
    }
    
    @GetMapping
    public Result findAll() {
        return new Result(hashtagRepository.findAll()
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
