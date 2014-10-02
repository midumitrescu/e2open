package com.e2open.interview.deliveryplan.service;

import com.e2open.interview.deliveryplan.dao.JpaBasedDeliveryPlanDao;
import com.e2open.interview.deliveryplan.domain.DeliveryPlan;
import com.e2open.interview.deliveryplan.domain.SortOrder;
import com.e2open.interview.deliveryplan.domain.querydetails.PaginationDetails;
import com.e2open.interview.deliveryplan.domain.querydetails.SortingDetails;
import com.e2open.interview.deliveryplan.json.DeliveryPlanItem;
import com.e2open.interview.deliveryplan.utils.PaginationSupport;
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
    public Collection<DeliveryPlanItem> readDeliveryPlans(PaginationSupport paginationSupport, String sortColumn) {
        if(sortColumn == null) {
            return readDeliveryPlans(paginationSupport);
        }
        PaginationDetails pagination = adaptPaginationInfo(paginationSupport);
        List<DeliveryPlan> deliveryPlans = deliveryPlanDao.findDeliveryPlans(pagination, SortingDetails.onFieldWithOrder(sortColumn, SortOrder.ASC));
        return convertToDeliveryPlanItems(deliveryPlans);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<DeliveryPlanItem> readDeliveryPlans(PaginationSupport paginationSupport) {
        PaginationDetails pagination = adaptPaginationInfo(paginationSupport);
        List<DeliveryPlan> deliveryPlans = deliveryPlanDao.findDeliveryPlans(pagination);
        return convertToDeliveryPlanItems(deliveryPlans);
    }

    private PaginationDetails adaptPaginationInfo(PaginationSupport paginationSupport) {
        return PaginationDetails.startingFromWithNumberOfElements(paginationSupport.firstElement(), paginationSupport.maximumNumberOfElements());
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
