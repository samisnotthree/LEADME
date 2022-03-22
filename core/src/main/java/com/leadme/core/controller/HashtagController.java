package com.leadme.core.controller;

@RestController
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;
    private final HashtagRepository hashtagRepository;
  
    @Transactional
    @PostMapping("/addHashtag")
    public Long addHashtag(@RequestBody HashtagDto hashtagDto) {
        return hashtagService.addHashtag(hashtagDto.toEntity());
    }
    
    @GetMapping("/findHashtag")
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
    @PostMapping("deleteHashtag")
    public void deleteHashtag(@RequestBody HashtagDto hashtagDto) {
        hashtagService.deleteHashtag(hashtagDto.toEntity());
    }
}
