package com.oneandone.dummy.service;

import com.oneandone.dummy.dao.DeliveryPlanDao;
import com.oneandone.dummy.domain.DeliveryPlan;
import com.oneandone.dummy.json.DeliveryPlanItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DeliveryPlanServiceImpl implements DeliveryPlanService {

    private DeliveryPlanDao deliveryPlanDao;

    @Override
    public Collection<DeliveryPlanItem> readDeliveryPlan() {
        List<DeliveryPlan> deliveryPlans = deliveryPlanDao.findAllDeliveryPlans();
        return convertToDeliveryPlanItems(deliveryPlans);
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
