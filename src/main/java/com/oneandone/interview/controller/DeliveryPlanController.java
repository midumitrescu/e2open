package com.oneandone.interview.controller;

import com.oneandone.interview.json.DeliveryPlanItem;
import com.oneandone.interview.service.DeliveryPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class DeliveryPlanController {

    private final DeliveryPlanService deliveryPlanService;

    @Autowired
    public DeliveryPlanController(DeliveryPlanService deliveryPlanService) {
        this.deliveryPlanService = deliveryPlanService;
    }

    @RequestMapping(value = "/deliveryPlans")
    public HttpEntity<Collection<DeliveryPlanItem>> getAllDeliveryPlans() {
        Collection<DeliveryPlanItem> deliveryPlanItems = deliveryPlanService.readDeliveryPlans();
        addSelfRef(deliveryPlanItems);
        return new ResponseEntity<Collection<DeliveryPlanItem>>(deliveryPlanItems, HttpStatus.OK);
    }

    @RequestMapping(value = "/deliveryPlans/{deliveryNumber}")
    public HttpEntity<DeliveryPlanItem> getDeliveryPlan(@PathVariable Long deliveryNumber) {
        DeliveryPlanItem singleDeliveryPlanItem = deliveryPlanService.readSinglePlan(deliveryNumber);

        addSelfRef(singleDeliveryPlanItem);
        return new ResponseEntity<DeliveryPlanItem>(singleDeliveryPlanItem, HttpStatus.OK);
    }

    private void addSelfRef(DeliveryPlanItem onObject) {
        final HttpEntity<DeliveryPlanItem> deliveryPlanRestPath = methodOn(DeliveryPlanController.class).getDeliveryPlan(onObject.getDeliveryNumber());
        Link methodParams = linkTo(deliveryPlanRestPath).withSelfRel();
        onObject.add(methodParams);
    }

    private void addSelfRef(Collection<DeliveryPlanItem> onObjects) {
        for(DeliveryPlanItem item : onObjects) {
            Link methodParams = linkTo(methodOn(DeliveryPlanController.class).getDeliveryPlan(item.getDeliveryNumber())).withSelfRel();
            item.add(methodParams);
        }
    }
}
