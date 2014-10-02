package com.e2open.interview.deliveryplan.json;

import com.e2open.interview.deliveryplan.domain.DeliveryPlan;
import org.joda.time.DateTime;
import org.springframework.hateoas.ResourceSupport;

public class DeliveryPlanItem extends ResourceSupport {

    final private long deliveryId;
    final private long deliveryNumber;
    final private String part;
    final private String depot;
    final private String customer;
    final private Integer quantity;
    final private String dueDateDay;
    final private String dueDateMonth;
    final private String dueDateYear;

    final private String deliveryDateDateDay;
    final private String deliveryDateDateMonth;
    final private String deliveryDateDateYear;

    final private int margin;
    final private String currency;

    public DeliveryPlanItem(DeliveryPlan delivery) {
        assert delivery != null : "DeliveryPlan should not be null at this point!";
        this.deliveryId = delivery.getId();
        this.deliveryNumber = delivery.getDeliveryNumber();
        this.part = delivery.getPart().getName();
        this.depot = delivery.getDepot().getAddress();
        this.customer = delivery.getCustomer().getName();
        this.quantity = delivery.getQuantity();

        final DateTime dueDate = new DateTime(delivery.getDueDate());
        this.dueDateDay = String.valueOf(dueDate.getDayOfMonth());
        this.dueDateMonth = String.valueOf(dueDate.getMonthOfYear());
        this.dueDateYear = String.valueOf(dueDate.getYear());

        if(delivery.getDeliveryDate() != null) {
            final DateTime deliveryDate = new DateTime(delivery.getDeliveryDate());
            this.deliveryDateDateDay = String.valueOf(deliveryDate.getDayOfMonth());
            this.deliveryDateDateMonth = String.valueOf(deliveryDate.getMonthOfYear());
            this.deliveryDateDateYear = String.valueOf(deliveryDate.getYear());
        } else {
            this.deliveryDateDateDay = "";
            this.deliveryDateDateMonth = "";
            this.deliveryDateDateYear = "";
        }
        this.margin = delivery.getMarginValue();
        this.currency = delivery.getCurrency().name();
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public long getDeliveryNumber() {
        return deliveryNumber;
    }

    public String getPart() {
        return part;
    }

    public String getDepot() {
        return depot;
    }

    public String getCustomer() {
        return customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDueDateDay() {
        return dueDateDay;
    }

    public String getDueDateMonth() {
        return dueDateMonth;
    }

    public String getDueDateYear() {
        return dueDateYear;
    }

    public String getDeliveryDateDateDay() {
        return deliveryDateDateDay;
    }

    public String getDeliveryDateDateMonth() {
        return deliveryDateDateMonth;
    }

    public String getDeliveryDateDateYear() {
        return deliveryDateDateYear;
    }

    public int getMargin() {
        return margin;
    }

    public String getCurrency() {
        return currency;
    }
}
