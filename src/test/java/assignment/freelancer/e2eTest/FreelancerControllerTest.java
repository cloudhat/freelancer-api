package assignment.freelancer.e2eTest;

import assignment.freelancer.domain.Freelancer;
import assignment.freelancer.domain.FreelancerAttributes;
import assignment.freelancer.dto.FreelancerAttributeRequest;
import assignment.freelancer.fixture.FreelancerFixture;
import assignment.freelancer.repository.FreeLancerJpaRepository;
import assignment.freelancer.utils.RDBCleanUp;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FreelancerControllerTest {

    @Autowired
    RDBCleanUp rdbCleanUp;

    @Autowired
    FreeLancerJpaRepository freeLancerJpaRepository;

    @BeforeEach
    void setUp() {
        rdbCleanUp.execute();
        this.setUpFreelancerData();
    }

    private void setUpFreelancerData() {
        Freelancer mostSkillfulFreelancer = FreelancerFixture.getMostSkillfulFreelancer();
        Freelancer mostViewedFreelancer = FreelancerFixture.getMostViewedFreelancer();
        Freelancer latestRegisteredFreelancer = FreelancerFixture.getLatestRegisteredFreelancer();

        freeLancerJpaRepository.saveFreelancer(mostSkillfulFreelancer);
        freeLancerJpaRepository.saveFreelancer(mostViewedFreelancer);
        freeLancerJpaRepository.saveFreelancer(latestRegisteredFreelancer);


        FreelancerAttributes mostSkillfulFreelancerAttributes = FreelancerFixture.getMostSkillfulFreelancerAttributes();
        mostSkillfulFreelancerAttributes.setFreelancer(mostSkillfulFreelancer);
        FreelancerAttributes mostViewedFreelancerAttributes = FreelancerFixture.getMostViewedFreelancerAttributes();
        mostViewedFreelancerAttributes.setFreelancer(mostViewedFreelancer);
        FreelancerAttributes latestRegisteredFreelancerAttributes = FreelancerFixture.getLatestRegisteredFreelancerAttributes();
        latestRegisteredFreelancerAttributes.setFreelancer(latestRegisteredFreelancer);

        freeLancerJpaRepository.saveFreelancerAttributes(mostSkillfulFreelancerAttributes);
        freeLancerJpaRepository.saveFreelancerAttributes(mostViewedFreelancerAttributes);
        freeLancerJpaRepository.saveFreelancerAttributes(latestRegisteredFreelancerAttributes);
    }

    @Nested
    class HappyCase {

        @DisplayName("프리랜서의 목록을 조회한다")
        @Test
        void normalCase1() {
            //given
            String uri = String.format("/freelancers?sortType=%s", FreelancerAttributeRequest.SortType.TECH_SKILL);
            //when
            ExtractableResponse<Response> response = RestAssured.given()
                    .when().get(uri)
                    .then().log().all()
                    .extract();

            //then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.jsonPath().getInt("numberOfElements")).isEqualTo(3);
            assertThat(response.jsonPath().getList("content.name", String.class))
                    .containsExactly(FreelancerFixture.NAME_OF_MOST_SKILLFUL_OLDEST_FREELANCER, FreelancerFixture.NAME_OF_MOST_VIEWED_FREELANCER, FreelancerFixture.NAME_OF_LATEST_REGISTERED_LEAST_SKILLFUL_FREELANCER);
        }
    }

    @Nested
    class FailCase {

        @DisplayName("잘못된 sortType 을 요청한다")
        @Test
        void errorCase1() {
            //given
            String uri = String.format("/freelancers?sortType=%s", FreelancerAttributeRequest.SortType.TECH_SKILL + "INVALID_STRING");

            //when
            ExtractableResponse<Response> response = RestAssured.given()
                    .when().log().all()
                    .get(uri)
                    .then().log().all()
                    .extract();

            //then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("잘못된 pageSize를 요청한다")
        @Test
        void errorCase2() {
            //given
            String uri = String.format("/freelancers?sortType=%s&pageSize=%d", FreelancerAttributeRequest.SortType.TECH_SKILL, FreelancerAttributeRequest.MAX_PAGE_SIZE + 1);

            //when
            ExtractableResponse<Response> response = RestAssured.given()
                    .when().log().all()
                    .get(uri)
                    .then().log().all()
                    .extract();

            //then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

}
