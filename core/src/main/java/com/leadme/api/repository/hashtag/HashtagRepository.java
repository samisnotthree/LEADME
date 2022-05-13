package com.leadme.api.repository.hashtag;

import com.leadme.api.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository  extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findByName(String name);
    List<Hashtag> findByNameContains(String name);

//    @Query(value =
//             " select h"
//           + " from"
//           + " Hashtag h"
//           + " left join fetch ("
//           + "     select ph.hashtagId, count(*) as progHashtagCount"
//           + "     from h.progHashtag ph"
//           + "     group by ph.hashtagId"
//           + "     order by ph.progHashtagCount desc"
//           + " ) cnt"
//           + " limit 10"
//          )
//    List<Hashtag> findPopularHashtags();
}
