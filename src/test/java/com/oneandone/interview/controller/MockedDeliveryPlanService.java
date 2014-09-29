package com.oneandone.interview.controller;

import com.oneandone.interview.domain.Currency;
import com.oneandone.interview.domain.Customer;
import com.oneandone.interview.domain.DeliveryPlan;
import com.oneandone.interview.domain.Depot;
import com.oneandone.interview.domain.Part;
import com.oneandone.interview.json.DeliveryPlanItem;
import com.oneandone.interview.service.DeliveryPlanService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Mocks {@link DeliveryPlanService} for testing pf {@link com.oneandone.interview.controller.DeliveryPlanControllerTest}
 */
@Service
public class MockedDeliveryPlanService  implements DeliveryPlanService {

    final long MOCK_DELIVERY_PLAN_NUMBER = 9999L;
    @Override
    public Collection<DeliveryPlanItem> readDeliveryPlans() {
        return Arrays.asList(readSinglePlan(MOCK_DELIVERY_PLAN_NUMBER));
    }

    @Override
    public DeliveryPlanItem readSinglePlan(Long deliveryPlanNumber) {
        return new DeliveryPlanItem(getSingleJPADeliveryPlan(deliveryPlanNumber));
    }

    private DeliveryPlan getSingleJPADeliveryPlan(Long deliveryPlanNumber) {
        DeliveryPlan deliveryPlan = new DeliveryPlan();
        deliveryPlan.setDeliveryNumber(deliveryPlanNumber);
        deliveryPlan.setCustomer(getSingleCustomer());
        deliveryPlan.setDepot(getSingleDepot());
        deliveryPlan.setDeliveryDate(today());
        deliveryPlan.setDeliveryDate(yesterday());
        deliveryPlan.setPart(getSinglePart());
        deliveryPlan.setQuantity(11111);
        deliveryPlan.setCurrency(Currency.YEN);
        return deliveryPlan;
    }

    private Part getSinglePart() {
        final Part part = new Part();
        part.setId(1000);
        part.setName("TestPart");
        return part;
    }

    private Date yesterday() {
        DateTime today = DateTime.now();
        return today.minusDays(1).toDate();
    }

    private Date today() {
        DateTime today = DateTime.now();
        return today.toDate();
    }

    private Depot getSingleDepot() {
        final Depot depot = new Depot();
        depot.setId(10);
        depot.setAddress("TestAddress");
        return depot;
    }

    private Customer getSingleCustomer() {
        final Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Name");
        return customer;
    }
}
