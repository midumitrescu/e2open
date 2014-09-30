package com.oneandone.interview.utils;

public class ImmutablePaginationSupport implements PaginationInfo {

    private final int pageNumber;
    private final int maxPageSize;
    /** Default value of 0 */
    private final int actualPageSize;
    private final int totalNumberOfElements;

    private ImmutablePaginationSupport(int pageNumber, int pageSize, int actualPageSize, int totalNumberOfElements) {
        assert pageNumber > 0 : "Page number cannot be negative";
        assert pageSize > 0 : "Maximum Page must be a positive int, to avoid infinite number of pages";
        assert pageNumber > -1 : "Number of elements cannot be negative";
        this.pageNumber = pageNumber;
        this.maxPageSize = pageSize;
        this.totalNumberOfElements = totalNumberOfElements;
        this.actualPageSize = actualPageSize;
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

    private boolean pagesAreFilledPerfectly() {
        return totalNumberOfElements % maxPageSize == 0;
    }

    public static class Builder {
        private int pageNumber;
        private int maxPageSize;
        private int actualPageSize = 0;
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

        public Builder actualPageSize(int actualPageSize) {
            this.actualPageSize = actualPageSize;
            return this;
        }

        public Builder totalNumberOfElements(int totalNumberOfElements) {
            this.totalNumberOfElements = totalNumberOfElements;
            return this;
        }

        public ImmutablePaginationSupport build() {
            return new ImmutablePaginationSupport(pageNumber, maxPageSize, actualPageSize, totalNumberOfElements);
        }
    }


}
