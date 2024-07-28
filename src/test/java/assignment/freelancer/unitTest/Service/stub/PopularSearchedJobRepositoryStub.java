package assignment.freelancer.unitTest.Service.stub;

import assignment.freelancer.domain.PopularSearchedJob;
import assignment.freelancer.repository.PopularSearchedJobRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopularSearchedJobRepositoryStub implements PopularSearchedJobRepository {

    public static HashMap<String, Integer> popularSearchedJobMap = new HashMap<>();

    @Override
    public void increaseSearchCount(String jobName, int viewCountDelta) {
        popularSearchedJobMap.put(jobName, popularSearchedJobMap.getOrDefault(jobName, 0) + viewCountDelta);
    }

    @Override
    public List<PopularSearchedJob> getMostSearchedJobs(int size) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(popularSearchedJobMap.entrySet());
        entries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        return entries.stream()
                .limit(size)
                .map(entry -> new PopularSearchedJob(entry.getKey(), entry.getValue()))
                .toList();
    }
}
