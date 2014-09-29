package com.oneandone.interview.service;

import com.oneandone.interview.dao.DeliveryPlanDao;
import com.oneandone.interview.domain.DeliveryPlan;
import com.oneandone.interview.json.DeliveryPlanItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JpaBasedDeliveryPlanService implements DeliveryPlanService {

    private DeliveryPlanDao deliveryPlanDao;

    @Override
    public Collection<DeliveryPlanItem> readDeliveryPlans() {
        List<DeliveryPlan> deliveryPlans = deliveryPlanDao.findAllDeliveryPlans();
        return convertToDeliveryPlanItems(deliveryPlans);
    }

    @Override
    public DeliveryPlanItem readSinglePlan(Long deliveryPlanNumber) {
        final DeliveryPlan singleDelivery = deliveryPlanDao.getSingleDeliveryPlanByNumber(deliveryPlanNumber);
        return new DeliveryPlanItem(singleDelivery);
    }

    private List<DeliveryPlanItem> convertToDeliveryPlanItems(List<DeliveryPlan> deliveryPlans) {
        List<DeliveryPlanItem> result = new ArrayList<DeliveryPlanItem>();
        for(DeliveryPlan deliveryPlan : deliveryPlans) {
             result.add(new DeliveryPlanItem(deliveryPlan));
        }
        return result;
    }

    @Resource
    public void setDeliveryPlanDao(DeliveryPlanDao deliveryPlanDao) {
        this.deliveryPlanDao = deliveryPlanDao;
    }
}
