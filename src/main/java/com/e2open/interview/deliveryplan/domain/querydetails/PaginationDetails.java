package com.e2open.interview.deliveryplan.domain.querydetails;

public class PaginationDetails {

    private final int firstElement;
    private final int numberOfElements;

    private PaginationDetails(int firstElement, int numberOfElements) {
        this.firstElement = firstElement;
        this.numberOfElements = numberOfElements;
    }

    public int getFirstElement() {
        return firstElement;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public static  PaginationDetails startingFromWithNumberOfElements(int firstElement, int numberOfElements) {
      return new PaginationDetails(firstElement, numberOfElements);
    }
}
