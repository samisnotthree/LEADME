package com.leadme.api.service;

import com.leadme.api.dto.sdto.ProgHashtagsDto;
import com.leadme.api.entity.Hashtag;
import com.leadme.api.entity.Prog;
import com.leadme.api.repository.hashtag.HashtagQueryRepository;
import com.leadme.api.repository.hashtag.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagQueryRepository hashtagQueryRepository;

    /**
     *  해시태그 등록
     */
    @Transactional
    public Long addHashtag(Hashtag hashtag) {
        Optional.ofNullable(hashtag.getName()).orElseThrow(() -> new IllegalStateException("해시태그명은 필수 입력 값입니다."));

        if (!validateDuplicateHashtag(hashtag)) {
            throw new IllegalStateException("이미 존재하는 해시태그입니다.");
        }
        return hashtagRepository.save(hashtag).getHashtagId();
    }

    /**
     *  프로그램에서의 사용 횟수 top 10인 해시태그 조회
     */
    public List<ProgHashtagsDto> searchPopularHashtags() {
        List<ProgHashtagsDto> progHashtags = hashtagQueryRepository.searchHashtagsWithCount();

//        return progHashtags.stream()
//                .sorted(Comparator.comparing(ProgHashtagsDto::getCount).reversed())
//                .limit(10)
//                .collect(Collectors.toList());

        Collections.sort(progHashtags, new CustomComparator().reversed());
        return progHashtags;
    }

    static class CustomComparator implements Comparator<ProgHashtagsDto> {
        @Override
        public int compare(ProgHashtagsDto o1, ProgHashtagsDto o2) {
            if (o1.getCount() > o2.getCount()) {
                return 1;
            } else if (o1.getCount() < o2.getCount()) {
                return -1;
            }
            return 0;
        }
    }

    /**
     *  해시태그 중복 체크
     */
    public boolean validateDuplicateHashtag(Hashtag hashtag) {
        List<Hashtag> findHashtags = hashtagRepository.findByName(hashtag.getName());
        return findHashtags.isEmpty();
    }

    /**
     *  해시태그 삭제(delete)
     */
    @Transactional
    public void deleteHashtag(Long hashtagId) {
        Optional.ofNullable(hashtagId).orElseThrow(() -> new IllegalStateException("해시태그 정보가 올바르지 않습니다."));
        Optional<Hashtag> hashtag = hashtagRepository.findById(hashtagId);
        hashtag.ifPresentOrElse(
                h -> hashtagRepository.deleteById(h.getHashtagId()),
                () -> hashtag.orElseThrow(() -> new IllegalStateException("존재하지 않는 해시태그입니다."))
        );
    }
}
