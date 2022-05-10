package com.leadme.api.service;

import com.leadme.api.entity.Hashtag;
import com.leadme.api.entity.Prog;
import com.leadme.api.entity.ProgHashtag;
import com.leadme.api.repository.hashtag.HashtagRepository;
import com.leadme.api.repository.progHashtag.ProgHashtagRepository;
import com.leadme.api.repository.prog.ProgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgHashtagService {
    private final ProgHashtagRepository progHashtagRepository;
    private final ProgRepository progRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public ProgHashtag addProgHashtag(Long progId, Long hashtagId) {
        Prog foundProg = progRepository.findById(progId).get();
        Hashtag foundHashtag = hashtagRepository.findById(hashtagId).get();

        if (isExistProgHashtag(foundProg, foundHashtag)) {
            throw new IllegalStateException("이미 등록되어 있는 해시태그입니다.");
        }
        ProgHashtag progHashtag = ProgHashtag.builder()
                                        .prog(foundProg)
                                        .hashtag(foundHashtag)
                                        .build();
        return progHashtagRepository.save(progHashtag);
    }

    public boolean isExistProgHashtag(Prog prog, Hashtag hashtag) {
        return !progHashtagRepository.findByProgAndHashtag(prog, hashtag).isEmpty();
    }

    @Transactional
    public void deleteProgHashtag(Long progHashtagId) {
        progHashtagRepository.deleteById(progHashtagId);
    }
}
