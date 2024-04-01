package com.Urban_India.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseFilter {

    public static final int DEFAULT_START_PAGE_NO = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    int per;
    int page;
    String[] sortBy;
    boolean ascending = true;
    // Used for search
    String query;
    Pageable pageable;
    boolean paginate = true;

    public Pageable getPageable() {
        if (Objects.isNull(pageable)) {
            int pageNo = this.page != 0 ? this.page : DEFAULT_START_PAGE_NO;
            int pageSize = this.per != 0 ? this.per : DEFAULT_PAGE_SIZE;
            if(!paginate){
                pageNo = 0;
                pageSize = Integer.MAX_VALUE;
            }
            Sort.Direction sortDirection = this.ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
            if(Objects.nonNull(sortBy)) {
                Sort sort = Sort.by(sortDirection,sortBy);
                this.pageable = PageRequest.of(pageNo, pageSize, sort);
            }else{
                this.pageable = PageRequest.of(pageNo, pageSize);
            }
        }
        return this.pageable;
    }
}
