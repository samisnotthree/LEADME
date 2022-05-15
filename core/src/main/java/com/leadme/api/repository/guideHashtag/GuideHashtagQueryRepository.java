package com.leadme.api.repository.guideHashtag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class GuideHashtagQueryRepository {

    private final JPAQueryFactory queryFactory;

    public GuideHashtagQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    public List<GuideHashtagDto> searchGuideHashtagRank() {
//        return queryFactory
//                .select(Projections.fields(GuideHashtagDto.class,
//                        guideHashtag.hashtagId,
//                        guideHashtag.hashtagId.count().as("count"))
//                )
//                .from(guideHashtag)
//                .groupBy(guideHashtag.hashtagId)
//                .fetch();
//    }
}
