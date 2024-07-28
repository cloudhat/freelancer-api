package assignment.freelancer.fixture;

import assignment.freelancer.domain.Freelancer;
import assignment.freelancer.domain.FreelancerAttributes;

import java.time.LocalDateTime;

public class FreelancerFixture {

    public static final String NAME_OF_MOST_SKILLFUL_OLDEST_FREELANCER = "기술능력이 뛰어난, 가장 오래전에 등록된 프리랜서";
    public static final String NAME_OF_MOST_VIEWED_FREELANCER = "가장 많이 조회된 프리랜서";
    public static final String NAME_OF_LATEST_REGISTERED_LEAST_SKILLFUL_FREELANCER = "가장 최근 등록된 프리랜서";

    public static Freelancer getMostSkillfulFreelancer() {
        return new Freelancer(NAME_OF_MOST_SKILLFUL_OLDEST_FREELANCER, LocalDateTime.now().minusDays(2));
    }

    public static Freelancer getMostViewedFreelancer() {
        return new Freelancer(NAME_OF_MOST_VIEWED_FREELANCER, LocalDateTime.now().minusDays(1));
    }

    public static Freelancer getLatestRegisteredFreelancer() {
        return new Freelancer(NAME_OF_LATEST_REGISTERED_LEAST_SKILLFUL_FREELANCER, LocalDateTime.now());
    }

    public static FreelancerAttributes getMostSkillfulFreelancerAttributes() {
        return new FreelancerAttributes(10, 0);
    }

    public static FreelancerAttributes getMostViewedFreelancerAttributes() {
        return new FreelancerAttributes(1, 100);
    }

    public static FreelancerAttributes getLatestRegisteredFreelancerAttributes() {
        return new FreelancerAttributes(0, 0);
    }

}
