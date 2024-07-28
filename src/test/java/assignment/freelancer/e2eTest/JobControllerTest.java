package assignment.freelancer.e2eTest;

import assignment.freelancer.domain.PopularSearchedJob;
import assignment.freelancer.dto.PopularSearchedJobResponse;
import assignment.freelancer.repository.PopularSearchedJobRedisRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JobControllerTest {

    @LocalServerPort
    private int port;

    @Container
    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.2")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {

        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
        }

        stringRedisTemplate.delete(PopularSearchedJobRedisRepository.ZSET_KEY);

    }

    /**
     * given : 업무를 조회하고
     * when  : 인기 업무 검색 키워드를 조회하면
     * then  : 조회수의 역순으로 키워드를 응답한다
     */
    @DisplayName("인기 업무 조회")
    @Test
    void normalCase1() {
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

        for (PopularSearchedJob job : jobs) {
            searchJobStep(job.getJobName(), job.getSearchCount());
        }

        //when
        ExtractableResponse<Response> response = RestAssured.given()
                .when().get("jobs/popular?size=10")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        List<PopularSearchedJobResponse> responseJobs = response.jsonPath().getList(".", PopularSearchedJobResponse.class);
        for (int i = 0; i < jobs.size(); i++) {
            PopularSearchedJob expectedJob = jobs.get(i);
            PopularSearchedJobResponse actualJobResponse = responseJobs.get(i);

            assertThat(actualJobResponse.getJobName()).isEqualTo(expectedJob.getJobName());
            assertThat(actualJobResponse.getSearchCount()).isEqualTo(expectedJob.getSearchCount());
        }
    }

    void searchJobStep(String jobName, int iter) {

        for (int i = 0; i < iter; i++) {
            ExtractableResponse<Response> response = RestAssured.given()
                    .when().get("/jobs/search?jobName=" + jobName)
                    .then()
                    .extract();
        }

    }


}
