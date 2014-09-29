package com.oneandone.interview.service;

import com.oneandone.interview.json.DeliveryPlanItem;

import java.util.Collection;

public interface DeliveryPlanService {

    Collection<DeliveryPlanItem> readDeliveryPlans();
    DeliveryPlanItem readSinglePlan(Long deliveryPlanNumber);
}
