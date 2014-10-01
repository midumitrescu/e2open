package com.oneandone.interview.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void testPreviousPage_3Pages() {
        ImmutablePaginationSupport threeFullPages = paginationBuilder().
                pageNumber(3).maxPageSize(10).totalNumberOfElements(30).build();
        Assert.assertEquals("30 elements, 10 elements per page still means 3 pages", 3, threeFullPages.totalNumberOfPages());
        Assert.assertEquals("Current page is 3", 3, threeFullPages.currentPageNumber());
        Assert.assertEquals("Previous page is 2", 2, threeFullPages.previousPage().currentPageNumber());
        Assert.assertEquals("Previous previous page is 1", 1, threeFullPages.previousPage().previousPage().currentPageNumber());
        Assert.assertNull("For three pages, we have reached the end of cycle", threeFullPages.previousPage().previousPage().previousPage());
        Assert.assertNull("For three full pages, we don't have any next", threeFullPages.nextPage());
    }

    @Test
    public void testNextPage_2IncompletePages() {
        ImmutablePaginationSupport twoIncompletePages = paginationBuilder().
                pageNumber(1).maxPageSize(10).totalNumberOfElements(11).build();
        Assert.assertEquals("11 elements, 10 elements per page still means 2 pages", 2, twoIncompletePages.totalNumberOfPages());
        Assert.assertEquals("Current page is 1", 1, twoIncompletePages.currentPageNumber());
        Assert.assertEquals("Next page is 2", 2, twoIncompletePages.nextPage().currentPageNumber());
        Assert.assertEquals("Previous from next from 1 is page 1", 1, twoIncompletePages.nextPage().previousPage().currentPageNumber());
        Assert.assertNull("No zero page", twoIncompletePages.previousPage());
        Assert.assertNull("No Last page", twoIncompletePages.nextPage().nextPage());
    }

    @Test
    public void test_isLastPage_isFirstPage_1Page() {
        ImmutablePaginationSupport onePage = paginationBuilder().
                pageNumber(1).maxPageSize(10).totalNumberOfElements(10).build();
        Assert.assertTrue("1 page, first page", onePage.isFirstPage());
        Assert.assertTrue("1 page, last page", onePage.isLastPage());
    }

    @Test
     public void test_isLastPage_isFirstPage_2Pages() {
        ImmutablePaginationSupport firstPage = paginationBuilder().
                pageNumber(1).maxPageSize(1).totalNumberOfElements(2).build();
        Assert.assertTrue("1st page, first page", firstPage.isFirstPage());
        Assert.assertFalse("1 page, not last page", firstPage.isLastPage());

        Assert.assertFalse("2nd page, not first page", firstPage.nextPage().isFirstPage());
        Assert.assertTrue("2nd page, last page", firstPage.nextPage().isLastPage());
    }

    @Test
     public void test_isLastPage_isFirstPage_3Pages() {
        ImmutablePaginationSupport firstPage = paginationBuilder().
                pageNumber(1).maxPageSize(2).totalNumberOfElements(5).build();
        Assert.assertTrue("1st page, first page", firstPage.isFirstPage());
        Assert.assertFalse("1 page, not last page", firstPage.isLastPage());

        PaginationSupport secondPage = firstPage.nextPage();

        Assert.assertFalse("2nd page, not first page", secondPage.isFirstPage());
        Assert.assertFalse("2nd page, not last page", secondPage.isLastPage());

        PaginationSupport thirdPage = secondPage.nextPage();

        Assert.assertFalse("3rd page, not first page", thirdPage.isFirstPage());
        Assert.assertTrue("3rd page, last page", thirdPage.isLastPage());
    }

    @Test
     public void test_next3Pages_largeNumberOfPages() {
        ImmutablePaginationSupport tenPages = paginationBuilder().
                pageNumber(1).maxPageSize(10).totalNumberOfElements(95).build();

        List<PaginationSupport> next3Pages = tenPages.nextPages(3);
        Assert.assertEquals("We have enough room for 3 pages", 3, next3Pages.size());
        checkNextThreePages(next3Pages);

        List<PaginationSupport> next10Pages = tenPages.nextPages(10);
        Assert.assertEquals("We only have 9 pages left", 9, next10Pages.size());
        checkNextThreePages(next10Pages);
        Assert.assertEquals("We only have 9 pages left", 10, next10Pages.get(8).currentPageNumber());
    }

    @Test
    public void test_next_previous_3Pages_smallNumberOfPages() {
        ImmutablePaginationSupport firstPage = paginationBuilder().
                pageNumber(1).maxPageSize(1).totalNumberOfElements(2).build();

        List<PaginationSupport> next3Pages = firstPage.nextPages(3);
        Assert.assertEquals("Only 2 pages in total => only 1 next pages", 1, next3Pages.size());
        Assert.assertEquals("first next page should be 2", 2, next3Pages.get(0).currentPageNumber());
        PaginationSupport secondPage = firstPage.nextPage();
        next3Pages = secondPage.nextPages(3);
        Assert.assertEquals("Second page is last page", 0, next3Pages.size());
        List<PaginationSupport> previous3Pages = firstPage.previousPages(3);
        Assert.assertEquals("No previous pages for first page", 0, previous3Pages.size());
        previous3Pages = secondPage.previousPages(3);
        Assert.assertEquals("Second page has only 1 previous page", 1, previous3Pages.size());
    }

    private void checkNextThreePages(List<PaginationSupport> next3Pages) {
        Assert.assertEquals("first next page should be 2", 2, next3Pages.get(0).currentPageNumber());
        Assert.assertEquals("second next page should be 3", 3, next3Pages.get(1).currentPageNumber());
        Assert.assertEquals("third next page should be 4", 4, next3Pages.get(2).currentPageNumber());
    }

    private ImmutablePaginationSupport.Builder paginationBuilder() {
        return ImmutablePaginationSupport.Builder.instance();
    }
}