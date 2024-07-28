package assignment.freelancer.unitTest.Repository;

import assignment.freelancer.domain.PopularSearchedJob;
import assignment.freelancer.repository.PopularSearchedJobRedisRepository;
import assignment.freelancer.repository.PopularSearchedJobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(PopularSearchedJobRedisRepository.class)
@Testcontainers
@DataRedisTest
public class PopularSearchedJobRedisRepositoryTest {

    @Container
    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.2")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Autowired
    PopularSearchedJobRepository sut;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {
        stringRedisTemplate.delete(PopularSearchedJobRedisRepository.ZSET_KEY);
    }

    @DisplayName("검색어를 조회하면 조회수가 증가한다")
    @Test
    void normalCase1() {

        //given
        String jobName = "번역 업무";
        int iter = 10;

        //when
        for (int i = 0; i < iter; i++) {
            sut.increaseSearchCount(jobName, 1);
        }

        //then
        PopularSearchedJob popularSearchedJob = sut.getMostSearchedJobs(10).get(0);
        assertThat(popularSearchedJob.getJobName()).isEqualTo(jobName);
        assertThat(popularSearchedJob.getSearchCount()).isEqualTo(iter);

    }

    @DisplayName("많이 검색한 순서대로 조회된다")
    @Test
    void normalCase2() {

        //given
        List<PopularSearchedJob> jobs = new ArrayList<>();
        jobs.add(new PopularSearchedJob("번역 업무", 10));
        jobs.add(new PopularSearchedJob("개발 업무", 9));
        jobs.add(new PopularSearchedJob("디자인 업무", 8));
        jobs.add(new PopularSearchedJob("마케팅 업무", 7));
        jobs.add(new PopularSearchedJob("회계 업무", 6));
        jobs.add(new PopularSearchedJob("법률 업무", 5));
        jobs.add(new PopularSearchedJob("교육 업무", 4));
        jobs.add(new PopularSearchedJob("의료 업무", 3));
        jobs.add(new PopularSearchedJob("상담 업무", 2));
        jobs.add(new PopularSearchedJob("연구 업무", 1));

        // 각 업무의 조회수를 설정합니다.
        for (PopularSearchedJob job : jobs) {
            sut.increaseSearchCount(job.getJobName(), job.getSearchCount());
        }

        //when
        List<PopularSearchedJob> topSearchedJobs = sut.getMostSearchedJobs(10);

        //then
        for (int i = 0; i < jobs.size(); i++) {
            PopularSearchedJob expectedJob = jobs.get(i);
            PopularSearchedJob actualJob = topSearchedJobs.get(i);

            assertThat(actualJob.getJobName()).isEqualTo(expectedJob.getJobName());
            assertThat(actualJob.getSearchCount()).isEqualTo(expectedJob.getSearchCount());
        }
    }

    @DisplayName("10개 이상 조회를 요청해도 10개만 조회된다")
    @Test
    void normalCase3() {

        //given
        List<PopularSearchedJob> jobs = new ArrayList<>();
        jobs.add(new PopularSearchedJob("청소 업무", 11));
        jobs.add(new PopularSearchedJob("번역 업무", 10));
        jobs.add(new PopularSearchedJob("개발 업무", 9));
        jobs.add(new PopularSearchedJob("디자인 업무", 8));
        jobs.add(new PopularSearchedJob("마케팅 업무", 7));
        jobs.add(new PopularSearchedJob("회계 업무", 6));
        jobs.add(new PopularSearchedJob("법률 업무", 5));
        jobs.add(new PopularSearchedJob("교육 업무", 4));
        jobs.add(new PopularSearchedJob("의료 업무", 3));
        jobs.add(new PopularSearchedJob("상담 업무", 2));
        jobs.add(new PopularSearchedJob("연구 업무", 1));

        // 각 업무의 조회수를 설정합니다.
        for (PopularSearchedJob job : jobs) {
            sut.increaseSearchCount(job.getJobName(), job.getSearchCount());
        }

        //when
        List<PopularSearchedJob> topSearchedJobs = sut.getMostSearchedJobs(15);

        //then
        assertThat(topSearchedJobs.size()).isEqualTo(PopularSearchedJobRedisRepository.MAX_SEARCH_SIZE);
    }
}
