package com.e2open.interview.deliveryplan.domain.querydetails;

import com.e2open.interview.deliveryplan.domain.SortOrder;
import org.springframework.util.StringUtils;

public class SortingDetails {

    private final String sortFieldName;
    private final SortOrder sortOrder;

    private SortingDetails(String sortFieldName, SortOrder sortOrder) {
        if(!StringUtils.hasText(sortFieldName)) {
            throw new IllegalArgumentException("When sorting is enabled, column must be specified");
        }
        this.sortFieldName = sortFieldName;
        this.sortOrder = sortOrder;
    }

    public static SortingDetails onFieldWithOrder(String sortFieldName, SortOrder sortOrder) {
        return new SortingDetails(sortFieldName, sortOrder);
    }

    public String getSortFieldName() {
        return sortFieldName;
    }

    public boolean isAscendent() {
        return SortOrder.ASC == sortOrder;
    }
}
