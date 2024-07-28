package assignment.freelancer.service;

import assignment.freelancer.domain.FreelancerAttributes;
import assignment.freelancer.dto.FreelancerAttributeRequest;
import assignment.freelancer.dto.FreelancerAttributeResponse;
import assignment.freelancer.repository.FreeLancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeLancerService {

    private final FreeLancerRepository freeLancerRepository;

    public Page<FreelancerAttributeResponse> getFreelancerAttributeResponse(FreelancerAttributeRequest freelancerAttributeRequest) {

        Page<FreelancerAttributes> freelancerAttributes = freeLancerRepository.findPagedFreelancerAttributes(freelancerAttributeRequest);
        List<FreelancerAttributeResponse> freelancerAttributeResponseList = freelancerAttributes.stream().map(FreelancerAttributeResponse::fromEntity).toList();

        return new PageImpl<>(freelancerAttributeResponseList, freelancerAttributeRequest.getPageRequest(), freelancerAttributes.getTotalElements());
    }

}
