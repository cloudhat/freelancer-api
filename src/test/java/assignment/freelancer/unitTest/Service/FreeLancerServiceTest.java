package assignment.freelancer.unitTest.Service;

import assignment.freelancer.domain.Freelancer;
import assignment.freelancer.domain.FreelancerAttributes;
import assignment.freelancer.dto.FreelancerAttributeRequest;
import assignment.freelancer.dto.FreelancerAttributeResponse;
import assignment.freelancer.fixture.FreelancerFixture;
import assignment.freelancer.repository.FreeLancerRepository;
import assignment.freelancer.service.FreeLancerService;
import assignment.freelancer.unitTest.Service.stub.FreeLancerRepositoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FreeLancerServiceTest {

    private static final FreeLancerRepository freeLancerRepositoryStub = new FreeLancerRepositoryStub();
    FreeLancerService freeLancerService = new FreeLancerService(freeLancerRepositoryStub);

    @BeforeEach
    void setUp(){
        Freelancer mostSkillfulFreelancer = FreelancerFixture.getMostSkillfulFreelancer();
        Freelancer mostViewedFreelancer = FreelancerFixture.getMostViewedFreelancer();
        Freelancer latestRegisteredFreelancer = FreelancerFixture.getLatestRegisteredFreelancer();

        freeLancerRepositoryStub.saveFreelancer(mostSkillfulFreelancer);
        freeLancerRepositoryStub.saveFreelancer(mostViewedFreelancer);
        freeLancerRepositoryStub.saveFreelancer(latestRegisteredFreelancer);


        FreelancerAttributes mostSkillfulFreelancerAttributes = FreelancerFixture.getMostSkillfulFreelancerAttributes();
        mostSkillfulFreelancerAttributes.setFreelancer(mostSkillfulFreelancer);
        FreelancerAttributes mostViewedFreelancerAttributes = FreelancerFixture.getMostViewedFreelancerAttributes();
        mostViewedFreelancerAttributes.setFreelancer(mostViewedFreelancer);
        FreelancerAttributes latestRegisteredFreelancerAttributes = FreelancerFixture.getLatestRegisteredFreelancerAttributes();
        latestRegisteredFreelancerAttributes.setFreelancer(latestRegisteredFreelancer);

        freeLancerRepositoryStub.saveFreelancerAttributes(mostSkillfulFreelancerAttributes);
        freeLancerRepositoryStub.saveFreelancerAttributes(mostViewedFreelancerAttributes);
        freeLancerRepositoryStub.saveFreelancerAttributes(latestRegisteredFreelancerAttributes);
    }

    @DisplayName("프리랜서 특성 entity를 dto로 정상적으로 변환하는 case")
    @Test
    void normalCase1() {
        //given
        FreelancerAttributeRequest freelancerAttributeRequest = new FreelancerAttributeRequest("TECH_SKILL", true, 0, 10);

        //when
        Page<FreelancerAttributeResponse> freelancerAttributeResponse = freeLancerService.getFreelancerAttributeResponse(freelancerAttributeRequest);

        //then
        assertThat(freelancerAttributeResponse.getTotalElements()).isEqualTo(3);

    }


}
