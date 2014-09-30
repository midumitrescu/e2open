package com.oneandone.interview.dao;

import com.oneandone.interview.domain.DeliveryPlan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/test-applicationContext.xml")
@ActiveProfiles("daoTest")
public class JpaBasedDeliveryPlanDaoTest {

    @Resource
    private JpaBasedDeliveryPlanDao deliveryPlanDao;

    @Test
    public void testCountDeliveryPlans() {
        Assert.assertEquals("Inserted by hand 5 Delivery Plans", Long.valueOf(5), deliveryPlanDao.countDeliveryPlans());
    }

    @Test
    public void testFindAllDeliveryPlans_5Elements() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(0, 100);
        Assert.assertEquals("5 elements in Test Mode", 5, allDeliveryPlans.size());
    }

    @Test
    public void testPagination_3Elements() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(0, 3);
        Assert.assertEquals("3 elements requested", 3, allDeliveryPlans.size());
    }

    @Test
    public void testPagination_lastElement_paginatedBy3() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(4, 3);
        Assert.assertEquals("4 elements requested, but because start point was 4, only one available", 1, allDeliveryPlans.size());
    }

    @Test
    public void testPagination_lastElement_paginatedBy5() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(4, 5);
        Assert.assertEquals("4 elements requested, but because start point was 4, only one available", 1, allDeliveryPlans.size());
    }
}