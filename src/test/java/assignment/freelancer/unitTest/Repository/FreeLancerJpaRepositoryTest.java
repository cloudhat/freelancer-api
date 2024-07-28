package assignment.freelancer.unitTest.Repository;

import assignment.freelancer.domain.Freelancer;
import assignment.freelancer.domain.FreelancerAttributes;
import assignment.freelancer.dto.FreelancerAttributeRequest;
import assignment.freelancer.fixture.FreelancerFixture;
import assignment.freelancer.repository.FreeLancerJpaRepository;
import assignment.freelancer.utils.RDBCleanUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class FreeLancerJpaRepositoryTest {

    @Autowired
    private RDBCleanUp rdbCleanUp;

    @Autowired
    private FreeLancerJpaRepository sut;

    @BeforeEach
    void setUp() {
        rdbCleanUp.execute();

        Freelancer mostSkillfulFreelancer = FreelancerFixture.getMostSkillfulFreelancer();
        Freelancer mostViewedFreelancer = FreelancerFixture.getMostViewedFreelancer();
        Freelancer latestRegisteredFreelancer = FreelancerFixture.getLatestRegisteredFreelancer();

        sut.saveFreelancer(mostSkillfulFreelancer);
        sut.saveFreelancer(mostViewedFreelancer);
        sut.saveFreelancer(latestRegisteredFreelancer);


        FreelancerAttributes mostSkillfulFreelancerAttributes = FreelancerFixture.getMostSkillfulFreelancerAttributes();
        mostSkillfulFreelancerAttributes.setFreelancer(mostSkillfulFreelancer);
        FreelancerAttributes mostViewedFreelancerAttributes = FreelancerFixture.getMostViewedFreelancerAttributes();
        mostViewedFreelancerAttributes.setFreelancer(mostViewedFreelancer);
        FreelancerAttributes latestRegisteredFreelancerAttributes = FreelancerFixture.getLatestRegisteredFreelancerAttributes();
        latestRegisteredFreelancerAttributes.setFreelancer(latestRegisteredFreelancer);

        sut.saveFreelancerAttributes(mostSkillfulFreelancerAttributes);
        sut.saveFreelancerAttributes(mostViewedFreelancerAttributes);
        sut.saveFreelancerAttributes(latestRegisteredFreelancerAttributes);

    }


    @DisplayName("프리랜서 목록 조회 정상 case")
    @ParameterizedTest
    @MethodSource("FreelancerAttributeRequestParameter")
    void normalCase1(String sortType, Boolean descending, String expectedName) {
        //given
        FreelancerAttributeRequest freelancerAttributeRequest = new FreelancerAttributeRequest(sortType, descending, 0, 10);

        //when
        Page<FreelancerAttributes> freelancerAttributes = sut.findPagedFreelancerAttributes(freelancerAttributeRequest);

        //then
        Freelancer firstFreelancer = freelancerAttributes.getContent().get(0).getFreelancer();
        assertThat(firstFreelancer.getName()).isEqualTo(expectedName);
    }

    private static Stream<Arguments> FreelancerAttributeRequestParameter() {
        return Stream.of(
                Arguments.of("TECH_SKILL", true, FreelancerFixture.NAME_OF_MOST_SKILLFUL_OLDEST_FREELANCER),
                Arguments.of("TECH_SKILL", false, FreelancerFixture.NAME_OF_LATEST_REGISTERED_LEAST_SKILLFUL_FREELANCER),
                Arguments.of("VIEW_COUNT", true, FreelancerFixture.NAME_OF_MOST_VIEWED_FREELANCER),
                Arguments.of("VIEW_COUNT", false, FreelancerFixture.NAME_OF_LATEST_REGISTERED_LEAST_SKILLFUL_FREELANCER),
                Arguments.of("CREATE_DATE", true, FreelancerFixture.NAME_OF_LATEST_REGISTERED_LEAST_SKILLFUL_FREELANCER),
                Arguments.of("CREATE_DATE", false, FreelancerFixture.NAME_OF_MOST_SKILLFUL_OLDEST_FREELANCER)
        );
    }


}
