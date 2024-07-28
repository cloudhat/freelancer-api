package assignment.freelancer.service;

import assignment.freelancer.dto.PopularSearchedJobResponse;
import assignment.freelancer.repository.PopularSearchedJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularSearchedJobService {

    private final PopularSearchedJobRepository popularSearchedJobRepository;

    public List<PopularSearchedJobResponse> getPopularSearchedJobResponses(int size) {
        return popularSearchedJobRepository.getMostSearchedJobs(size).stream()
                .map(PopularSearchedJobResponse::fromEntity)
                .toList();
    }

    public String searchJob(String jobName) {
        saveSearchJobLog(jobName);
        popularSearchedJobRepository.increaseSearchCount(jobName, 1);

        return String.format("요청주신 검색어 '%s'에 대한 결과가 없습니다.", jobName);
    }

    private void saveSearchJobLog(String jobName) {
        System.out.println(String.format("검색내역 %s를 DB에 저장 완료", jobName));
    }

}
