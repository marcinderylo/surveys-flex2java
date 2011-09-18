package org.adaptiveplatform.codegenerator.utils;

public final class StringUtils {

    private StringUtils() {
    }

    public static String packageToPath(String pckage) {
        return org.apache.commons.lang.StringUtils.replaceChars(pckage, '.', '/');
    }
}
