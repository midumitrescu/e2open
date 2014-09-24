package com.oneandone.dummy.json;

import com.oneandone.dummy.domain.DeliveryPlan;

public class DeliveryPlanItem {

    private String id;
    private String customerName;

    public DeliveryPlanItem(DeliveryPlan plan) {

    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }
}
