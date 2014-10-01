package com.oneandone.interview.controller;

import com.oneandone.interview.json.DeliveryPlanItem;
import com.oneandone.interview.json.PageOf;
import com.oneandone.interview.service.DeliveryPlanService;
import com.oneandone.interview.utils.ImmutablePaginationSupport;
import com.oneandone.interview.utils.PaginationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/deliveryPlans")
public class DeliveryPlanResource extends AbstractAutoPaginatingResource<DeliveryPlanItem> {

    private final DeliveryPlanService deliveryPlanService;

    @Autowired
    public DeliveryPlanResource(DeliveryPlanService deliveryPlanService) {
        this.deliveryPlanService = deliveryPlanService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<PageOf<DeliveryPlanItem>> listDeliveryPlans(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                  @RequestParam(value = "size", defaultValue = "-1", required = false) int size,
                                                                  @RequestParam(value = "sort", required = false) String sort ) {

        Long totalNumberOfElements = deliveryPlanService.readDeliveryPlansNumber();

        int preferredPageSize = idealPageSize(size);
        PaginationSupport paginationSupport = paginationBuilder().pageNumber(page).maxPageSize(preferredPageSize).totalNumberOfElements(totalNumberOfElements.intValue()).build();

        Collection<DeliveryPlanItem> deliveryPlanItems = deliveryPlanService.readDeliveryPlans(paginationSupport, sort);


        PageOf<DeliveryPlanItem> paginatedDeliveryPlans = enablePagination(deliveryPlanItems, paginationSupport);

        return new ResponseEntity<PageOf<DeliveryPlanItem>>(paginatedDeliveryPlans, HttpStatus.OK);
    }

    private ImmutablePaginationSupport.Builder paginationBuilder() {
        return ImmutablePaginationSupport.Builder.instance();
    }

    @RequestMapping(value = "/{deliveryNumber}")
    @ResponseBody
    public HttpEntity<DeliveryPlanItem> getDeliveryPlan(@PathVariable Long deliveryNumber) {
        DeliveryPlanItem singleDeliveryPlanItem = deliveryPlanService.readSinglePlan(deliveryNumber);

        addSelfRef(singleDeliveryPlanItem);
        return new ResponseEntity<DeliveryPlanItem>(singleDeliveryPlanItem, HttpStatus.OK);
    }

    private void addSelfRef(DeliveryPlanItem onObject) {
        final HttpEntity<DeliveryPlanItem> deliveryPlanRestPath = methodOn(DeliveryPlanResource.class).getDeliveryPlan(onObject.getDeliveryNumber());
        Link methodParams = linkTo(deliveryPlanRestPath).withSelfRel();
        onObject.add(methodParams);
    }

    protected void addSelfRef(Collection<DeliveryPlanItem> onObjects) {
        for(DeliveryPlanItem item : onObjects) {
            Link methodParams = linkTo(methodOn(DeliveryPlanResource.class).getDeliveryPlan(item.getDeliveryNumber())).withSelfRel();
            item.add(methodParams);
        }
    }

    private PageOf<DeliveryPlanItem> enablePagination(Collection<DeliveryPlanItem> items, PaginationSupport currentPageCoordinates) {
        addSelfRef(items);
        PageOf<DeliveryPlanItem> currentPage = new PageOf<DeliveryPlanItem>(items);
        currentPage.setPageNumber(currentPageCoordinates.currentPageNumber());
        HttpEntity<PageOf<DeliveryPlanItem>> currentPageReference = getPageReference(currentPageCoordinates);
        currentPage.add(linkTo(currentPageReference).withSelfRel());

        addAdjacentPagesRef(currentPage, currentPageCoordinates);
        return currentPage;
    }

    private HttpEntity<PageOf<DeliveryPlanItem>> getPageReference(PaginationSupport currentPageCoordinates) {
        return methodOn(DeliveryPlanResource.class).listDeliveryPlans(currentPageCoordinates.currentPageNumber(), currentPageCoordinates.maximumNumberOfElements(), "");
    }

    private void addAdjacentPagesRef(PageOf<DeliveryPlanItem> currentPage, PaginationSupport paginationSupport) {
        List<PaginationSupport> previous3 = paginationSupport.previousPages(3);
        List<PaginationSupport> next3 = paginationSupport.nextPages(3);
        addRelationship(currentPage, previous3, "previous");
        addRelationship(currentPage, next3, "next");
    }

    private void addRelationship(PageOf<DeliveryPlanItem> currentPage, List<PaginationSupport> previous3, String hint) {
        for(int i = 0; i < previous3.size(); i++) {
            PaginationSupport adjacentPageCoordinates = previous3.get(i);
            String relationship = hint ;
            if(i > 0) {
                relationship = relationship + "[" + (i+1) + "]";
            }
            currentPage.add(linkTo(getPageReference(adjacentPageCoordinates)).withRel(relationship));
        }
    }
}
