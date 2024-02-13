package com.banreservas.utils;

public class StringUtils {
    private StringUtils() {
    }

    public static String stringIsNullOrEmptyValidation(final String newValue, final String oldValue) {
        if(newValue == null || newValue.isEmpty()) {
            return oldValue;
        } else {
            return newValue;
        }
    }
}
