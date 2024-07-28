package assignment.freelancer.repository;

import assignment.freelancer.domain.PopularSearchedJob;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class PopularSearchedJobRedisRepository implements PopularSearchedJobRepository {

    private final StringRedisTemplate stringRedisTemplate;

    public final static String ZSET_KEY = "SEARCHED_JOB_RANKING";

    public final static int MAX_SEARCH_SIZE = 10;

    @Override
    public void increaseSearchCount(String jobName, int viewCountDelta) {
        ZSetOperations<String, String> zSetOps = stringRedisTemplate.opsForZSet();
        zSetOps.incrementScore(ZSET_KEY, jobName, viewCountDelta);
    }

    @Override
    public List<PopularSearchedJob> getMostSearchedJobs(int size) {

        //최대 10개만 검색 가능하도록 제한
        size = Math.min(size, MAX_SEARCH_SIZE);

        ZSetOperations<String, String> zSetOps = stringRedisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> topSearchedJobs = zSetOps.reverseRangeWithScores(ZSET_KEY, 0, size - 1);

        return topSearchedJobs.stream()
                .map(job -> new PopularSearchedJob(job.getValue(), job.getScore().intValue()))
                .toList();
    }
}
