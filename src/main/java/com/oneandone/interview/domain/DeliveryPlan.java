package com.oneandone.interview.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NamedQueries({
  @NamedQuery(
    name = DeliveryPlan.COUNT_ITEMS,
    query = "SELECT COUNT(o) FROM DeliveryPlan o"
  ),
  @NamedQuery(
    name = DeliveryPlan.FIND_ALL,
    query = "SELECT o FROM DeliveryPlan o"
  ),
  @NamedQuery(
    name = DeliveryPlan.FIND_BY_DELIVERY_NUMBER,
    query = "SELECT o FROM DeliveryPlan o WHERE o.deliveryNumber = :" + DeliveryPlan.DELIVERY_NUMBER_PARAM_NAME
  )
})
@Entity
@Table(name = "delivery_plans")
public class DeliveryPlan {
    public static final String COUNT_ITEMS = "countAll";
    public static final String FIND_ALL = "findAll";
    public static final String FIND_BY_DELIVERY_NUMBER = "findByDeliveryNumber";
    public static final String DELIVERY_NUMBER_PARAM_NAME = "deliveryNumber";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     */
    @NotNull
    @Column(unique = true)
    private Long deliveryNumber;

    /**
     */
    @ManyToOne
    private Part part;

    /**
     */
    @NotNull
    @ManyToOne
    private Depot depot;

    /**
     */
    @NotNull
    private Integer quantity;

    /**
     */
    @NotNull
    @ManyToOne
    private Customer customer;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dueDate;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date deliveryDate;

    /**
     */
    @NotNull
    private Integer marginValue;
    /**
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Version
    @Column(name = "version")
    private Integer version;

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setCurrency(Currency curreny) {
        this.currency = curreny;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setMarginValue(Integer marginValue) {
        this.marginValue = marginValue;
    }

    public Long getDeliveryNumber() {
        return this.deliveryNumber;
    }

    public void setDeliveryNumber(Long deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Part getPart() {
        return this.part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Depot getDepot() {
        return this.depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getMarginValue() {
        return this.marginValue;
    }

}
