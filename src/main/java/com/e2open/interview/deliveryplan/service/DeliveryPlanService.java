package com.e2open.interview.deliveryplan.service;

import com.e2open.interview.deliveryplan.json.DeliveryPlanItem;
import com.e2open.interview.deliveryplan.utils.PaginationSupport;

import java.util.Collection;

public interface DeliveryPlanService {

    Collection<DeliveryPlanItem> readDeliveryPlans(PaginationSupport paginationSupport, String sortColumn);
    Collection<DeliveryPlanItem> readDeliveryPlans(PaginationSupport paginationSupport);
    DeliveryPlanItem readSinglePlan(Long deliveryPlanNumber);
    Long readDeliveryPlansNumber();
}
