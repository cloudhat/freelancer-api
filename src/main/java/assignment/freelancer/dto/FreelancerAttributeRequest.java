package assignment.freelancer.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FreelancerAttributeRequest {

    public static final int MAX_PAGE_SIZE = 10;

    private SortType sortType;
    private Boolean descending = true;
    private Integer pageNum = 0;
    public Integer pageSize = MAX_PAGE_SIZE;

    public FreelancerAttributeRequest(String sortType, Boolean descending, Integer pageNum, Integer pageSize) {
        this.sortType = SortType.valueOf(sortType);
        this.descending = (descending != null) ? descending : this.descending;
        this.pageNum = (pageNum != null) ? pageNum : this.pageNum;

        if (pageSize != null) {
            if (pageSize > MAX_PAGE_SIZE) {
                throw new IllegalArgumentException(String.format("페이지의 최대 크기는 %d 입니다.", MAX_PAGE_SIZE));
            }
            this.pageSize = pageSize;
        }
    }

    public enum SortType {
        TECH_SKILL,
        VIEW_COUNT,
        CREATE_DATE

    }

    public PageRequest getPageRequest() {
        return PageRequest.of(this.pageNum, this.pageSize);
    }
}
