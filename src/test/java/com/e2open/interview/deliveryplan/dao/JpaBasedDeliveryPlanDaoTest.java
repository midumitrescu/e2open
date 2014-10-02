package com.e2open.interview.deliveryplan.dao;

import com.e2open.interview.deliveryplan.domain.DeliveryPlan;
import com.e2open.interview.deliveryplan.domain.SortOrder;
import com.e2open.interview.deliveryplan.domain.querydetails.SortingDetails;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static com.e2open.interview.deliveryplan.domain.querydetails.PaginationDetails.startingFromWithNumberOfElements;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/test-applicationContext.xml")
@ActiveProfiles("daoTests")
public class JpaBasedDeliveryPlanDaoTest {

    @Resource
    private JpaBasedDeliveryPlanDao deliveryPlanDao;

    @Test
    public void testCountDeliveryPlans() {
        Assert.assertEquals("Inserted by hand 5 Delivery Plans", Long.valueOf(5), deliveryPlanDao.countDeliveryPlans());
    }

    @Test
    public void testFindAllDeliveryPlans_5Elements() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(startingFromWithNumberOfElements(0, 100));
        Assert.assertEquals("5 elements in Test Mode", 5, allDeliveryPlans.size());
    }

    @Test
    public void testSorting_5Elements_ASC() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(startingFromWithNumberOfElements(0, 100), SortingDetails.onFieldWithOrder("deliveryNumber", SortOrder.ASC));
        Assert.assertEquals("5 elements in Test Mode", 5, allDeliveryPlans.size());
        Long previousDeliveryNumber = Long.MIN_VALUE;
        for(DeliveryPlan plan : allDeliveryPlans) {
            Assert.assertTrue("Order is ASC. Current DeliveryNumber is " + plan.getDeliveryNumber() + ", and previous was " + previousDeliveryNumber, plan.getDeliveryNumber() > previousDeliveryNumber);
            previousDeliveryNumber = plan.getDeliveryNumber();
        }
    }

    @Test
    public void testSorting_5Elements_DESC() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(startingFromWithNumberOfElements(0, 100), SortingDetails.onFieldWithOrder("deliveryNumber", SortOrder.DESC));
        Assert.assertEquals("5 elements in Test Mode", 5, allDeliveryPlans.size());
        Long previousDeliveryNumber = Long.MAX_VALUE;
        for(DeliveryPlan plan : allDeliveryPlans) {
            Assert.assertTrue("Order is Desc. Current DeliveryNumber is " + plan.getDeliveryNumber() + ", and previous was " + previousDeliveryNumber, plan.getDeliveryNumber() < previousDeliveryNumber);
            previousDeliveryNumber = plan.getDeliveryNumber();
        }
    }

    @Test
    public void testPagination_3Elements() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(startingFromWithNumberOfElements(0, 3));
        Assert.assertEquals("3 elements requested", 3, allDeliveryPlans.size());
    }

    @Test
    public void testPagination_lastElement_paginatedBy3() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(startingFromWithNumberOfElements(4, 3));
        Assert.assertEquals("4 elements requested, but because start point was 4, only one available", 1, allDeliveryPlans.size());
    }

    @Test
    public void testPagination_lastElement_paginatedBy5() {
        List<DeliveryPlan> allDeliveryPlans = deliveryPlanDao.findDeliveryPlans(startingFromWithNumberOfElements(4, 5));
        Assert.assertEquals("4 elements requested, but because start point was 4, only one available", 1, allDeliveryPlans.size());
    }
}