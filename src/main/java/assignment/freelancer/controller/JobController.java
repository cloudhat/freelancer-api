package assignment.freelancer.controller;

import assignment.freelancer.dto.PopularSearchedJobResponse;
import assignment.freelancer.service.PopularSearchedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    private final PopularSearchedJobService popularSearchedJobService;

    @GetMapping("/popular")
    public ResponseEntity<List<PopularSearchedJobResponse>> showFreelancerList(@RequestParam int size) {
        return ResponseEntity.ok(popularSearchedJobService.getPopularSearchedJobResponses(size));
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchJobs(@RequestParam String jobName) {
        return ResponseEntity.ok(popularSearchedJobService.searchJob(jobName));
    }

}
