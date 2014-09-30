package com.oneandone.interview.controller;

import com.oneandone.interview.json.DeliveryPlanItem;
import com.oneandone.interview.json.PageOf;
import com.oneandone.interview.service.DeliveryPlanService;
import com.oneandone.interview.utils.ImmutablePaginationSupport;
import com.oneandone.interview.utils.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class DeliveryPlanResource extends AbstractAutoPaginatingResource<DeliveryPlanItem> {

    private final DeliveryPlanService deliveryPlanService;

    @Autowired
    public DeliveryPlanResource(DeliveryPlanService deliveryPlanService) {
        this.deliveryPlanService = deliveryPlanService;
    }

    @RequestMapping(value = "/deliveryPlans", params = {"page", "size", "sort"})
    public HttpEntity<PageOf<DeliveryPlanItem>> listDeliveryPlans(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                  @RequestParam(value = "size", defaultValue = "-1", required = false) int size,
                                                                  @RequestParam(value = "sort", required = false) String sort ) {
        PaginationInfo paginationInfo = paginationBuilder().pageNumber(page).maxPageSize(size).totalNumberOfElements(getDefaultPageSize()).build();
        Collection<DeliveryPlanItem> deliveryPlanItems = deliveryPlanService.readDeliveryPlans(paginationInfo, sort);
        Long totalNumberOfElements = deliveryPlanService.readDeliveryPlansNumber();

        PageOf<DeliveryPlanItem> paginatedDeliveryPlans = enablePagination(deliveryPlanItems, page, size,  totalNumberOfElements);

        return new ResponseEntity<PageOf<DeliveryPlanItem>>(paginatedDeliveryPlans, HttpStatus.OK);
    }

    private ImmutablePaginationSupport.Builder paginationBuilder() {
        return ImmutablePaginationSupport.Builder.instance();
    }

    @RequestMapping(value = "/deliveryPlans/{deliveryNumber}")
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

    private PageOf<DeliveryPlanItem> enablePagination(Collection<DeliveryPlanItem> items, int page, int size, Long totalNumberOfElements) {
        addSelfRef(items);
        PageOf<DeliveryPlanItem> result = new PageOf<DeliveryPlanItem>(items);


        return result;
    }
}
