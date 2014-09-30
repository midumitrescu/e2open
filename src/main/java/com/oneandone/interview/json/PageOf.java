package com.oneandone.interview.json;

import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.Collections;

public class PageOf<Item extends ResourceSupport> {

    private final int elementsOnPage;
    private int pageNumber;
    private int totalNumberOfElements;
    private final Collection<Item> items;

    public PageOf(Collection<Item> items) {
        this.items = (items == null ? Collections.EMPTY_LIST : items);
        elementsOnPage = this.items.size();
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTotalNumberOfElements(int totalNumberOfElements) {
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public int getElementsOnPage() {
        return elementsOnPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    public Collection<Item> getItems() {
        return items;
    }
}
