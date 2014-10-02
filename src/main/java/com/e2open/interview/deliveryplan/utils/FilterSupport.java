package com.e2open.interview.deliveryplan.utils;

public class FilterSupport {
    private final String onColumn;
    private final String value;

    public FilterSupport(String onColumn, String value) {
        this.onColumn = onColumn;
        this.value = value;
    }

    public String getOnColumn() {
        return onColumn;
    }

    public String getValue() {
        return value;
    }
}
