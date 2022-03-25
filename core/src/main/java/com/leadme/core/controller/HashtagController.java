package com.leadme.core.controller;

import com.leadme.core.dto.HashtagDto;
import com.leadme.core.repository.HashtagRepository;
import com.leadme.core.service.HashtagService;
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
public class HashtagController {
    private final HashtagService hashtagService;
    private final HashtagRepository hashtagRepository;
  
    @Transactional
    @PostMapping("/hashtag")
    public Long addHashtag(@RequestBody HashtagDto hashtagDto) {
        return hashtagService.addHashtag(hashtagDto.toEntity());
    }
    
    @GetMapping("/hashtag")
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
    @DeleteMapping("hashtag")
    public void deleteHashtag(@RequestBody HashtagDto hashtagDto) {
        hashtagService.deleteHashtag(hashtagDto.toEntity());
    }
}
