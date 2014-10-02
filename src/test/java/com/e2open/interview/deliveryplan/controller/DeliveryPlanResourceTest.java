package com.e2open.interview.deliveryplan.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:META-INF/test-applicationContext.xml")
@ActiveProfiles("webTests")
public class DeliveryPlanResourceTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Value("${restPagination.defaultSize}")
    private int testConfiguredPaginationSize;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testDeliveryPlans_deliversArray_defaultParams() throws Exception {
        ResultActions prepareJson = mockMvc.perform(get("/deliveryPlans"));
        testItemsAreInArray(prepareJson);
        prepareJson.andExpect(jsonPath("$.items[" + testConfiguredPaginationSize + "]").doesNotExist());
        prepareJson.andExpect(jsonPath("$.elementsOnPage").value(testConfiguredPaginationSize));
        //starting from first page
        prepareJson.andExpect(jsonPath("$.pageNumber").value(1));
        //we have a self & next page
        prepareJson.andExpect(jsonPath("$.links").isArray());
        prepareJson.andExpect(jsonPath("$.links[0].rel").value("self"));
        prepareJson.andExpect(jsonPath("$.links[1].rel").value("next"));
    }

    @Test
    public void testDeliveryPlans_deliversArray_secondPage() throws Exception {
        ResultActions prepareJson = mockMvc.perform(get("/deliveryPlans?page=2"));
        testItemsAreInArray(prepareJson);
        // on second page, only one element
        prepareJson.andExpect(jsonPath("$.items[2]").doesNotExist());
        prepareJson.andExpect(jsonPath("$.elementsOnPage").value(1));
        //starting from first page
        prepareJson.andExpect(jsonPath("$.pageNumber").value(2));
        //we have a self & next page
        prepareJson.andExpect(jsonPath("$.links").isArray());
        prepareJson.andExpect(jsonPath("$.links[0].rel").value("self"));
        prepareJson.andExpect(jsonPath("$.links[1].rel").value("previous"));
    }

    @Test
    public void testDeliveryOnePlan() throws Exception {
        ResultActions prepareJson = mockMvc.perform(get("/deliveryPlans/200"));

        prepareJson.andExpect(jsonPath("$.items").doesNotExist());
        // links is an array with one element
        prepareJson.andExpect(jsonPath("$.links").isArray());
        prepareJson.andExpect(jsonPath("$.links[1]").doesNotExist());
        // on second page, only one element
        prepareJson.andExpect(jsonPath("$.deliveryNumber").value(200));
        ensureElementIsPresentInJson(prepareJson, "deliveryId", "deliveryNumber", "part", "depot",
                "customer", "quantity", "dueDateDay", "dueDateMonth", "dueDateYear", "margin", "currency"  );
    }

    private void ensureElementIsPresentInJson(ResultActions prepareJson, String ... parts) throws Exception {
        for(String part : parts) {
            prepareJson.andExpect(jsonPath("$." + part).exists());
        }
    }

    private void testItemsAreInArray(ResultActions prepareJson) throws Exception {
        prepareJson.andExpect(status().isOk());
        prepareJson.andExpect(jsonPath("$.items").isArray());
        prepareJson.andExpect(jsonPath("$.items[0]").exists());
    }
}