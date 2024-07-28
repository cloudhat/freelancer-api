package assignment.freelancer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "freelancerAttributes", indexes = {
        @Index(name = "idx_techSkillLevel", columnList = "techSkillLevel"),
        @Index(name = "idx_viewCount", columnList = "viewCount"),
})
public class FreelancerAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    int techSkillLevel;

    @Column(nullable = false)
    int viewCount;

    @OneToOne
    @JoinColumn(name = "freelancer_id", nullable = false)
    Freelancer freelancer;

    public FreelancerAttributes(int techSkillLevel, int viewCount) {
        this.techSkillLevel = techSkillLevel;
        this.viewCount = viewCount;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

}
