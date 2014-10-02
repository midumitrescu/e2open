package com.e2open.interview.deliveryplan.controller;

import com.e2open.interview.deliveryplan.json.DeliveryPlanItem;
import com.e2open.interview.deliveryplan.json.PageOf;
import com.e2open.interview.deliveryplan.service.DeliveryPlanService;
import com.e2open.interview.deliveryplan.utils.ImmutablePaginationSupport;
import com.e2open.interview.deliveryplan.utils.PaginationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/deliveryPlans")
public class DeliveryPlanResource extends AbstractAutoPaginatingResource<DeliveryPlanItem> {

    private final DeliveryPlanService deliveryPlanService;

    @Autowired
    public DeliveryPlanResource(DeliveryPlanService deliveryPlanService) {
        this.deliveryPlanService = deliveryPlanService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<PageOf<DeliveryPlanItem>> listDeliveryPlans(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                  @RequestParam(value = "size", defaultValue = "-1", required = false) int size,
                                                                  @RequestParam(value = "sort", defaultValue = "deliveryNumber", required = false) String sort ) {
        Long totalNumberOfElements = deliveryPlanService.readDeliveryPlansNumber();
        int preferredPageSize = idealPageSize(size);
        PaginationSupport paginationSupport = paginationBuilder().pageNumber(page).maxPageSize(preferredPageSize).totalNumberOfElements(totalNumberOfElements.intValue()).build();
        Collection<DeliveryPlanItem> deliveryPlanItems = deliveryPlanService.readDeliveryPlans(paginationSupport, sort);
        PageOf<DeliveryPlanItem> paginatedDeliveryPlans = enablePagination(deliveryPlanItems, paginationSupport, sort);
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

    @Override
    protected HttpEntity<PageOf<DeliveryPlanItem>> getPageReference(PaginationSupport currentPageCoordinates, String sort) {
        return methodOn(DeliveryPlanResource.class).listDeliveryPlans(currentPageCoordinates.currentPageNumber(), currentPageCoordinates.maximumNumberOfElements(), sort);
    }
}
