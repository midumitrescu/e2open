package com.oneandone.interview.service;

import com.oneandone.interview.json.DeliveryPlanItem;
import com.oneandone.interview.utils.PaginationInfo;

import java.util.Collection;

public interface DeliveryPlanService {

    Collection<DeliveryPlanItem> readDeliveryPlans(PaginationInfo paginationInfo, String sortColumn);
    DeliveryPlanItem readSinglePlan(Long deliveryPlanNumber);
    Long readDeliveryPlansNumber();
}
