package org.adaptiveplatform.codegenerator.utils;

public final class StringUtils {

    private StringUtils() {
    }

    public static String decapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
