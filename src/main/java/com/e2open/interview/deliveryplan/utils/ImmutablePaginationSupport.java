package com.e2open.interview.deliveryplan.utils;

import java.util.ArrayList;
import java.util.List;

public class ImmutablePaginationSupport implements PaginationSupport {

    private final int pageNumber;
    private final int maxPageSize;
    private final int totalNumberOfElements;

    private ImmutablePaginationSupport(int pageNumber, int pageSize, int totalNumberOfElements) {
        assert pageNumber > 0 : "Page number cannot be negative";
        assert pageSize > 0 : "Maximum Page must be a positive int, to avoid infinite number of pages";
        assert pageNumber > -1 : "Number of elements cannot be negative";
        this.pageNumber = pageNumber;
        this.maxPageSize = pageSize;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public int firstElement() {
        return (pageNumber - 1) * maxPageSize;
    }

    public int totalNumberOfPages() {
        int fullPages = totalNumberOfElements / maxPageSize;
        if(!pagesAreFilledPerfectly()) {
            fullPages++;
        }
        return fullPages;
    }

    @Override
    public int maximumNumberOfElements() {
        return maxPageSize;
    }

    @Override
    public int currentPageNumber() {
        return pageNumber;
    }

    @Override
    public boolean isFirstPage() {
        return pageNumber == 1;
    }

    @Override
    public boolean isLastPage() {
        return pageNumber * maxPageSize >= totalNumberOfElements;
    }

    @Override
    public PaginationSupport nextPage() {
        return isLastPage() ? null : new ImmutablePaginationSupport(pageNumber + 1, maxPageSize,totalNumberOfElements);
    }

    @Override
    public PaginationSupport previousPage() {
        return isFirstPage() ? null : new ImmutablePaginationSupport(pageNumber -1, maxPageSize, totalNumberOfElements);
    }

    @Override
    public List<PaginationSupport> nextPages(int maxRange) {
        validateMaxRange(maxRange);
        List<PaginationSupport> result = new ArrayList<PaginationSupport>(maxRange);
        PaginationSupport current = this.nextPage();
        for(int i = 0; i < maxRange; i++) {
           if(current == null) {
               break;
           }
            result.add(current);
            current = current.nextPage();
        }
        return result;
    }

    @Override
    public List<PaginationSupport> previousPages(int maxRange) {
        validateMaxRange(maxRange);
        List<PaginationSupport> result = new ArrayList<PaginationSupport>(maxRange);
        PaginationSupport current = this.previousPage();
        for(int i = 0; i < maxRange; i++) {
            if(current == null) {
                break;
            }
            result.add(current);
            current = current.previousPage();
        }
        return result;
    }

    private void validateMaxRange(int maxRange) {
        if(maxRange < 1) {
            throw new IllegalArgumentException("Value " + maxRange + " as maximum span for pagination is unacceptable!");
        }
    }

    private boolean pagesAreFilledPerfectly() {
        return totalNumberOfElements % maxPageSize == 0;
    }

    public static class Builder {
        private int pageNumber;
        private int maxPageSize;
        private int totalNumberOfElements;

        public static ImmutablePaginationSupport.Builder instance() {
            return new Builder();
        }

        public Builder pageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public Builder maxPageSize(int maxPageSize) {
            this.maxPageSize = maxPageSize;
            return this;
        }

        public Builder totalNumberOfElements(int totalNumberOfElements) {
            this.totalNumberOfElements = totalNumberOfElements;
            return this;
        }

        public ImmutablePaginationSupport build() {
            return new ImmutablePaginationSupport(pageNumber, maxPageSize, totalNumberOfElements);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutablePaginationSupport that = (ImmutablePaginationSupport) o;

        if (maxPageSize != that.maxPageSize) return false;
        if (pageNumber != that.pageNumber) return false;
        if (totalNumberOfElements != that.totalNumberOfElements) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pageNumber;
        result = 31 * result + maxPageSize;
        result = 31 * result + totalNumberOfElements;
        return result;
    }
}
