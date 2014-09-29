package com.oneandone.interview.dao;

import com.oneandone.interview.domain.DeliveryPlan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/persistenceMock/test-applicationContext.xml")
public class DeliveryPlanDaoTest {

    @Resource
    private DeliveryPlanDao deliveryPlanDao;

    @Test
    public void testCountDeliveryPlans() {
        Assert.assertEquals("Inserted by hand 5 Delivery Plans", Long.valueOf(5), deliveryPlanDao.countDeliveryPlans());
    }

    @Test
    public void testFindAllDeliveryPlans() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findAllDeliveryPlans();
        Assert.assertEquals("Not an empty list", 5, allDeliveryPlans.size());
    }
}