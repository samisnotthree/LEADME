package com.leadme.api.repository.hashtag;

import com.leadme.api.dto.GuideHashtagDto;
import com.leadme.api.dto.ProgHashtagDto;
import com.leadme.api.dto.sdto.ProgHashtagsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.leadme.api.entity.QGuideHashtag.guideHashtag;
import static com.leadme.api.entity.QHashtag.hashtag;
import static com.leadme.api.entity.QProg.prog;
import static com.leadme.api.entity.QProgHashtag.progHashtag;

@Repository
public class HashtagQueryRepository {
    private final JPAQueryFactory queryFactory;

    public HashtagQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ProgHashtagsDto> searchHashtagsWithCount() {
        return queryFactory
                .select(Projections.fields(ProgHashtagsDto.class,
                        hashtag.hashtagId,
                        hashtag.name,
                        progHashtag.progHashtagId.count().as("count"))
                )
                .from(progHashtag)
                .join(progHashtag.hashtag, hashtag)
                .groupBy(hashtag.hashtagId, hashtag.name)
                .fetch();
    }

}
