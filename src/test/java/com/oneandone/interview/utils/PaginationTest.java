package com.oneandone.interview.utils;

import org.junit.Assert;
import org.junit.Test;

public class PaginationTest {

    @Test(expected = AssertionError.class)
    public void testStupidInitialization_1() {
        paginationBuilder().pageNumber(0).maxPageSize(0).totalNumberOfElements(10).build();
    }

    @Test(expected = AssertionError.class)
    public void testStupidInitialization_2() {
        paginationBuilder().pageNumber(1).totalNumberOfElements(10).build();
    }

    @Test(expected = AssertionError.class)
    public void testFirstElement_firstPageStartsFromZero() {
        ImmutablePaginationSupport firstPage = paginationBuilder().
          pageNumber(-1).maxPageSize(10).totalNumberOfElements(100).build();
        Assert.assertEquals(0, firstPage.firstElement());
    }

    @Test
    public void testFirstElement_secondPageStartsFrom10() {
        ImmutablePaginationSupport firstPage = paginationBuilder().
          pageNumber(2).maxPageSize(10).totalNumberOfElements(100).build();
        Assert.assertEquals(10, firstPage.firstElement());
    }

    @Test
    public void testFirstElement_5thPageStartsFrom40() {
        ImmutablePaginationSupport firstPage = paginationBuilder().
          pageNumber(5).maxPageSize(10).totalNumberOfElements(100).build();
        Assert.assertEquals(40, firstPage.firstElement());
    }

    @Test
    public void testTotalNumberOfPages_perfectMatch() {
        ImmutablePaginationSupport tenFullPages = paginationBuilder().
          pageNumber(1).maxPageSize(10).totalNumberOfElements(100).build();
        Assert.assertEquals("100 elements, 10 elements per page means 10 pages", 10, tenFullPages.totalNumberOfPages());
    }

    @Test
    public void testTotalNumberOfPages_lowNumberOfElements() {
        ImmutablePaginationSupport tenFullPages = paginationBuilder().
          pageNumber(1).maxPageSize(10).totalNumberOfElements(5).build();
        Assert.assertEquals("5 elements, 10 elements per page means 1 page", 1, tenFullPages.totalNumberOfPages());
    }

    @Test
    public void testTotalNumberOfPages_lastPageIncomplete_oneElementExtra() {
        ImmutablePaginationSupport tenFullPages = paginationBuilder().
          pageNumber(1).maxPageSize(10).totalNumberOfElements(101).build();
        Assert.assertEquals("101 elements, 10 elements per page means 11 pages", 11, tenFullPages.totalNumberOfPages());
    }

    @Test
    public void testTotalNumberOfPages_lastPageIncomplete_nineElementsExtra() {
        ImmutablePaginationSupport tenFullPages = paginationBuilder().
          pageNumber(1).maxPageSize(10).totalNumberOfElements(109).build();
        Assert.assertEquals("109 elements, 10 elements per page means 11 pages", 11, tenFullPages.totalNumberOfPages());
    }

    @Test
    public void testTotalNumberOfPages_elevenPerfectMatch() {
        ImmutablePaginationSupport tenFullPages = paginationBuilder().
          pageNumber(1).maxPageSize(10).totalNumberOfElements(110).build();
        Assert.assertEquals("110 elements, 10 elements per page still means 11 pages", 11, tenFullPages.totalNumberOfPages());
    }

    private ImmutablePaginationSupport.Builder paginationBuilder() {
        return ImmutablePaginationSupport.Builder.instance();
    }
}