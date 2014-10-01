package com.oneandone.interview.controller;

import com.oneandone.interview.json.PageOf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;

public abstract class AbstractAutoPaginatingResource<Item extends ResourceSupport> {

    private int defaultPageSize;

    private PageOf<Item> enablePagination(Collection<Item> items, int pagNumber, Long totalNumberOfElements) {
        addSelfRef(items);
        PageOf<Item> pagination = new PageOf<Item>(items);
        pagination.setPageNumber(pagNumber);

        return pagination;
    }

    protected abstract void addSelfRef(Collection<Item> items);

    @Value("${restPagination.defaultSize}")
    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    protected int defaultPageSize() {
        return defaultPageSize;
    }

    protected int idealPageSize(int input) {
        return input < 1 ? defaultPageSize : input;
    }
}
