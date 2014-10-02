package com.e2open.interview.deliveryplan.controller;

import com.e2open.interview.deliveryplan.json.PageOf;
import com.e2open.interview.deliveryplan.utils.PaginationSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public abstract class AbstractAutoPaginatingResource<Item extends ResourceSupport> {

    private int defaultPageSize;

    protected PageOf<Item> enablePagination(Collection<Item> items, PaginationSupport currentPageCoordinates, String sort) {
        addSelfRef(items);
        PageOf<Item> currentPage = new PageOf<Item>(items);
        currentPage.setPageNumber(currentPageCoordinates.currentPageNumber());
        HttpEntity<PageOf<Item>> currentPageReference = getPageReference(currentPageCoordinates, sort);
        currentPage.add(linkTo(currentPageReference).withSelfRel());

        addAdjacentPagesRef(currentPage, currentPageCoordinates, sort);
        return currentPage;
    }

    protected abstract void addSelfRef(Collection<Item> items);

    /**
     * Add reference, for the current page coordinates and sort option. It cannot be handled in abstract class, because it depends on client actual mapped method
     */
    protected abstract HttpEntity<PageOf<Item>> getPageReference(PaginationSupport currentPageCoordinates, String sort);

    @Value("${restPagination.defaultSize}")
    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    private void addAdjacentPagesRef(PageOf<Item> currentPage, PaginationSupport paginationSupport, String sort) {
        List<PaginationSupport> previous3 = paginationSupport.previousPages(3);
        List<PaginationSupport> next3 = paginationSupport.nextPages(3);
        addRelationship(currentPage, previous3, "previous", sort);
        addRelationship(currentPage, next3, "next", sort);
    }

    private void addRelationship(PageOf<Item> currentPage, List<PaginationSupport> previous3, String hint, String sort) {
        for(int i = 0; i < previous3.size(); i++) {
            PaginationSupport adjacentPageCoordinates = previous3.get(i);
            String relationship = hint ;
            if(i > 0) {
                relationship = relationship + "[" + (i+1) + "]";
            }
            currentPage.add(linkTo(getPageReference(adjacentPageCoordinates, sort)).withRel(relationship));
        }
    }

    protected int defaultPageSize() {
        return defaultPageSize;
    }

    protected int idealPageSize(int input) {
        return input < 1 ? defaultPageSize : input;
    }
}
