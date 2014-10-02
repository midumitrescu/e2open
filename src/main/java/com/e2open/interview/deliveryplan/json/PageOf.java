package com.e2open.interview.deliveryplan.json;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PageOf<Item extends ResourceSupport> extends ResourceSupport {

    private final int elementsOnPage;
    private int pageNumber;
    private final Collection<Item> items;

    public PageOf(Collection<Item> items) {
        this.items = (items == null ? Collections.EMPTY_LIST : items);
        elementsOnPage = this.items.size();
    }

    public PageOf(int pageNumber, int elementsOnPage) {
        this.pageNumber = pageNumber;
        this.elementsOnPage = elementsOnPage;
        this.items = Collections.EMPTY_LIST;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getElementsOnPage() {
        return elementsOnPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public Collection<Item> getItems() {
        return new ArrayList<Item>(items);
    }
}
