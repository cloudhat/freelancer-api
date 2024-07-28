package assignment.freelancer.repository;

import assignment.freelancer.domain.Freelancer;
import assignment.freelancer.domain.FreelancerAttributes;
import assignment.freelancer.dto.FreelancerAttributeRequest;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static assignment.freelancer.domain.QFreelancer.freelancer;
import static assignment.freelancer.domain.QFreelancerAttributes.freelancerAttributes;

@RequiredArgsConstructor
@Repository
public class FreeLancerJpaRepository implements FreeLancerRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public Page<FreelancerAttributes> findPagedFreelancerAttributes(FreelancerAttributeRequest freelancerAttributeRequest) {
        PageRequest pageRequest = freelancerAttributeRequest.getPageRequest();

        JPQLQuery<FreelancerAttributes> countQuery = queryFactory
                .selectFrom(freelancerAttributes);

        List<FreelancerAttributes> content = countQuery
                .join(freelancerAttributes.freelancer, freelancer).fetchJoin()
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(freelancerAttributesOrderBy(freelancerAttributeRequest))
                .fetch();

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::fetchCount);
    }

    private OrderSpecifier[] freelancerAttributesOrderBy(FreelancerAttributeRequest freelancerAttributeRequest) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        Order order = freelancerAttributeRequest.getDescending() ? Order.DESC : Order.ASC;

        switch (freelancerAttributeRequest.getSortType()) {
            case TECH_SKILL -> orderSpecifiers.add(new OrderSpecifier(order, freelancerAttributes.techSkillLevel));
            case VIEW_COUNT -> orderSpecifiers.add(new OrderSpecifier(order, freelancerAttributes.viewCount));
            case CREATE_DATE -> orderSpecifiers.add(new OrderSpecifier(order, freelancer.createDate));

            default -> throw new IllegalArgumentException("Invalid sort type: " + freelancerAttributeRequest.getSortType());

        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    @Override
    @Transactional
    public Freelancer saveFreelancer(Freelancer freelancer){
        em.persist(freelancer);

        return freelancer;
    }

    @Override
    @Transactional
    public FreelancerAttributes saveFreelancerAttributes(FreelancerAttributes freelancerAttributes){
        em.persist(freelancerAttributes);

        return freelancerAttributes;
    }
}
