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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/rest")
public class DeliveryPlanResource extends AbstractAutoPaginatingResource<DeliveryPlanItem> {

    private final DeliveryPlanService deliveryPlanService;

    @Autowired
    public DeliveryPlanResource(DeliveryPlanService deliveryPlanService) {
        this.deliveryPlanService = deliveryPlanService;
    }

    @RequestMapping(value = "/deliveryPlans", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<PageOf<DeliveryPlanItem>> listDeliveryPlans(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                  @RequestParam(value = "size", defaultValue = "-1", required = false) int size,
                                                                  @RequestParam(value = "sort", required = false) String sort ) {

        Long totalNumberOfElements = deliveryPlanService.readDeliveryPlansNumber();

        int preferredPageSize = idealPageSize(size);
        PaginationSupport paginationSupport = paginationBuilder().pageNumber(page).maxPageSize(preferredPageSize).totalNumberOfElements(defaultPageSize()).build();

        Collection<DeliveryPlanItem> deliveryPlanItems = deliveryPlanService.readDeliveryPlans(paginationSupport, sort);


        PageOf<DeliveryPlanItem> paginatedDeliveryPlans = enablePagination(deliveryPlanItems, paginationSupport);

        return new ResponseEntity<PageOf<DeliveryPlanItem>>(paginatedDeliveryPlans, HttpStatus.OK);
    }

    private ImmutablePaginationSupport.Builder paginationBuilder() {
        return ImmutablePaginationSupport.Builder.instance();
    }

    @RequestMapping(value = "/deliveryPlans/{deliveryNumber}")
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

    private PageOf<DeliveryPlanItem> enablePagination(Collection<DeliveryPlanItem> items, PaginationSupport paginationSupport) {
        addSelfRef(items);
        PageOf<DeliveryPlanItem> pagination = new PageOf<DeliveryPlanItem>(items);
        pagination.setPageNumber(paginationSupport.);
        pagination.setTotalNumberOfElements(totalNumberOfElements);

        addPagesRef(pagination);
        return pagination;
    }

    private void addPagesRef(PageOf<DeliveryPlanItem> pagination) {
        HttpEntity<PageOf<DeliveryPlanItem>> pageOfHttpEntity = methodOn(DeliveryPlanResource.class).listDeliveryPlans(pagination.getPageNumber(), pagination.getElementsOnPage(), "");
        pagination.add(linkTo(pageOfHttpEntity).withSelfRel());
        if (pagination.isNotFirstPage()) {
            // add pages before
        }
    }
}
