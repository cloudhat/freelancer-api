package assignment.freelancer.unitTest.Service.stub;

import assignment.freelancer.domain.Freelancer;
import assignment.freelancer.domain.FreelancerAttributes;
import assignment.freelancer.dto.FreelancerAttributeRequest;
import assignment.freelancer.fixture.FreelancerFixture;
import assignment.freelancer.repository.FreeLancerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FreeLancerRepositoryStub implements FreeLancerRepository {

    private final AtomicLong freelancerIdGenerator = new AtomicLong(1);
    List<Freelancer> freelancerList = new ArrayList<>();

    private final AtomicLong freelancerAttributesIdGenerator = new AtomicLong(1);
    List<FreelancerAttributes> freelancerAttributesList = new ArrayList<>();


    @Override
    public Page<FreelancerAttributes> findPagedFreelancerAttributes(FreelancerAttributeRequest freelancerAttributeRequest) {


        return new PageImpl<>(freelancerAttributesList,freelancerAttributeRequest.getPageRequest(),freelancerAttributesList.size());
    }

    @Override
    public Freelancer saveFreelancer(Freelancer freelancer) {
        final Long id = freelancerIdGenerator.getAndIncrement();

        try{
            Field targetField = freelancer.getClass().getDeclaredField("id");
            targetField.setAccessible(true);
            targetField.set(freelancer, id);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        freelancerList.add(freelancer);
        return freelancer;
    }

    @Override
    public FreelancerAttributes saveFreelancerAttributes(FreelancerAttributes freelancerAttributes) {
        final Long id = freelancerAttributesIdGenerator.getAndIncrement();

        try{
            Field targetField = freelancerAttributes.getClass().getDeclaredField("id");
            targetField.setAccessible(true);
            targetField.set(freelancerAttributes, id);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        freelancerAttributesList.add(freelancerAttributes);
        return freelancerAttributes;
    }
}
