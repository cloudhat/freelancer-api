package assignment.freelancer.repository;

import assignment.freelancer.domain.Freelancer;
import assignment.freelancer.domain.FreelancerAttributes;
import assignment.freelancer.dto.FreelancerAttributeRequest;
import org.springframework.data.domain.Page;

public interface FreeLancerRepository {
    Page<FreelancerAttributes> findPagedFreelancerAttributes(FreelancerAttributeRequest freelancerAttributeRequest);

    Freelancer saveFreelancer(Freelancer freelancer);

    FreelancerAttributes saveFreelancerAttributes(FreelancerAttributes freelancerAttributes);
}
