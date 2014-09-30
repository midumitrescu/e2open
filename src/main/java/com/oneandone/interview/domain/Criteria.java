package com.oneandone.interview.domain;

public class Criteria {

    private int firstElement = 0;
    private int maxElements = 0;
    private String filterBy;

    public Criteria(int firstElement, int maxElements, String filterBy) {
        this.firstElement = firstElement;
        this.maxElements = maxElements;
        this.filterBy = filterBy;
    }
}
