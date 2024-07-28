package assignment.freelancer.repository;

import assignment.freelancer.domain.PopularSearchedJob;

import java.util.List;

public interface PopularSearchedJobRepository {

    void increaseSearchCount(String jobName, int viewCountDelta);

    List<PopularSearchedJob> getMostSearchedJobs(int size);
}
