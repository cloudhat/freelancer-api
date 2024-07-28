package assignment.freelancer.dto;

import assignment.freelancer.domain.FreelancerAttributes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class FreelancerAttributeResponse {

    private String name;
    private int techSkillLevel;
    private int viewCount;
    private LocalDateTime createDate;

    public static FreelancerAttributeResponse fromEntity(FreelancerAttributes freelancerAttributes) {
        FreelancerAttributeResponse freelancerAttributeResponse = new FreelancerAttributeResponse();

        freelancerAttributeResponse.name = freelancerAttributes.getFreelancer().getName();
        freelancerAttributeResponse.techSkillLevel = freelancerAttributes.getTechSkillLevel();
        freelancerAttributeResponse.viewCount = freelancerAttributes.getViewCount();
        freelancerAttributeResponse.createDate = freelancerAttributes.getFreelancer().getCreateDate();

        return freelancerAttributeResponse;
    }
}
