package assignment.freelancer.unitTest.Service;

import assignment.freelancer.dto.PopularSearchedJobResponse;
import assignment.freelancer.service.PopularSearchedJobService;
import assignment.freelancer.unitTest.Service.stub.PopularSearchedJobRepositoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PopularSearchJobServiceTest {
    PopularSearchedJobService popularSearchedJobService = new PopularSearchedJobService(new PopularSearchedJobRepositoryStub());

    @BeforeEach
    void setUp() {
        PopularSearchedJobRepositoryStub.popularSearchedJobMap.clear();
    }

    @DisplayName("특정 업무를 검색하면 조회 수가 증가한다")
    @Test
    void normalCase1() {

        //given
        String jobName = "번역 업무";
        int iter = 10;

        //when
        for (int i = 0; i < iter; i++) {
            popularSearchedJobService.searchJob(jobName);
        }

        //then
        PopularSearchedJobResponse popularSearchedJobResponse = popularSearchedJobService.getPopularSearchedJobResponses(10).get(0);
        assertThat(popularSearchedJobResponse.getJobName()).isEqualTo(jobName);
        assertThat(popularSearchedJobResponse.getSearchCount()).isEqualTo(iter);

    }
}
