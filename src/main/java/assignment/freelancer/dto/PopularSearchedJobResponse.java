package assignment.freelancer.dto;

import assignment.freelancer.domain.PopularSearchedJob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PopularSearchedJobResponse {
    
    private String jobName;
    private int searchCount;
    
    public static PopularSearchedJobResponse fromEntity(PopularSearchedJob popularSearchedJob){
        PopularSearchedJobResponse popularSearchedJobResponse = new PopularSearchedJobResponse();
        popularSearchedJobResponse.jobName = popularSearchedJob.getJobName();
        popularSearchedJobResponse.searchCount = popularSearchedJob.getSearchCount();

        return popularSearchedJobResponse;
    }
}
