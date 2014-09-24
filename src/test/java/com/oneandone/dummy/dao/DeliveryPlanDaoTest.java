package com.oneandone.dummy.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/test-applicationContext.xml")
public class DeliveryPlanDaoTest {

    @Resource
    private DeliveryPlanDao deliveryPlanDao;

    @Test
    public void testCountDeliveryPlans() throws Exception {
        System.out.println(deliveryPlanDao.countDeliveryPlans());

    }

    @Test
    public void testFindAllDeliveryPlans() throws Exception {

    }
}