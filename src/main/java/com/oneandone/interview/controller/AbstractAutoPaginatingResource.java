package com.oneandone.interview.controller;

import com.oneandone.interview.json.PageOf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;

public abstract class AbstractAutoPaginatingResource<Item extends ResourceSupport> {

    private int defaultPageSize;

    private PageOf<Item> enablePagination(Collection<Item> items, int pagNumber, int totalNumberOfElements) {
        addSelfRef(items);
        PageOf<Item> pagination = new PageOf<Item>(items);
        pagination.setPageNumber(pagNumber);
        pagination.setTotalNumberOfElements(totalNumberOfElements);

        return pagination;
    }

    protected abstract void addSelfRef(Collection<Item> items);

    @Value("${restPagination.defaultSize}")
    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    protected int getDefaultPageSize() {
        return defaultPageSize;
    }

}
