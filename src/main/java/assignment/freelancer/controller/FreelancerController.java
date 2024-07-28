package assignment.freelancer.controller;

import assignment.freelancer.dto.FreelancerAttributeRequest;
import assignment.freelancer.dto.FreelancerAttributeResponse;
import assignment.freelancer.service.FreeLancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/freelancers")
public class FreelancerController {

    private final FreeLancerService freeLancerService;

    @GetMapping
    public ResponseEntity<Page<FreelancerAttributeResponse>> showFreelancerList(FreelancerAttributeRequest freelancerAttributeRequest) {

        return ResponseEntity.ok(freeLancerService.getFreelancerAttributeResponse(freelancerAttributeRequest));
    }
}
