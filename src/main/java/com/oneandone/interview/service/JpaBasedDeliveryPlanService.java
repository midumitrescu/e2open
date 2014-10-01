package com.oneandone.interview.service;

import com.oneandone.interview.dao.JpaBasedDeliveryPlanDao;
import com.oneandone.interview.domain.DeliveryPlan;
import com.oneandone.interview.json.DeliveryPlanItem;
import com.oneandone.interview.utils.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JpaBasedDeliveryPlanService implements DeliveryPlanService {

    private final JpaBasedDeliveryPlanDao deliveryPlanDao;

    @Autowired
    public JpaBasedDeliveryPlanService(JpaBasedDeliveryPlanDao deliveryPlanDao) {
        this.deliveryPlanDao = deliveryPlanDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<DeliveryPlanItem> readDeliveryPlans(PaginationInfo paginationInfo, String sortColumn) {
        List<DeliveryPlan> deliveryPlans = deliveryPlanDao.findDeliveryPlans(paginationInfo.firstElement(), paginationInfo.maximumNumberOfElements());
        return convertToDeliveryPlanItems(deliveryPlans);
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryPlanItem readSinglePlan(Long deliveryPlanNumber) {
        DeliveryPlan singleDelivery = deliveryPlanDao.getSingleDeliveryPlanByNumber(deliveryPlanNumber);
        return new DeliveryPlanItem(singleDelivery);
    }

    @Override
    @Transactional(readOnly = true)
    public Long readDeliveryPlansNumber() {
        return deliveryPlanDao.countDeliveryPlans();
    }

    private List<DeliveryPlanItem> convertToDeliveryPlanItems(List<DeliveryPlan> deliveryPlans) {
        List<DeliveryPlanItem> result = new ArrayList<DeliveryPlanItem>();
        for(DeliveryPlan deliveryPlan : deliveryPlans) {
             result.add(new DeliveryPlanItem(deliveryPlan));
        }
        return result;
    }
}
