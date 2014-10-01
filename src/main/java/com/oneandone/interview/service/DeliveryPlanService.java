package com.oneandone.interview.service;

import com.oneandone.interview.json.DeliveryPlanItem;
import com.oneandone.interview.utils.PaginationSupport;

import java.util.Collection;

public interface DeliveryPlanService {

    Collection<DeliveryPlanItem> readDeliveryPlans(PaginationSupport paginationSupport, String sortColumn);
    DeliveryPlanItem readSinglePlan(Long deliveryPlanNumber);
    Long readDeliveryPlansNumber();
}
