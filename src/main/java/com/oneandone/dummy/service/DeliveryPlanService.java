package com.oneandone.dummy.service;

import com.oneandone.dummy.json.DeliveryPlanItem;

import java.util.Collection;

public interface DeliveryPlanService {

    Collection<DeliveryPlanItem> readDeliveryPlan();
}
