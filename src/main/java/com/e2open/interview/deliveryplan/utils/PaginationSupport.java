package com.e2open.interview.deliveryplan.utils;

import java.util.List;

public interface PaginationSupport {
    int firstElement();
    int totalNumberOfPages();
    int maximumNumberOfElements();
    int currentPageNumber();
    boolean isFirstPage();
    boolean isLastPage();
    PaginationSupport nextPage();
    PaginationSupport previousPage();
    List<PaginationSupport> nextPages(int maxRange);
    List<PaginationSupport> previousPages(int maxRange);
}
