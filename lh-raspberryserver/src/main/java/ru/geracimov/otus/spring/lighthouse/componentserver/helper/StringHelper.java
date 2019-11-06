package ru.geracimov.otus.spring.lighthouse.componentserver.helper;

import java.util.Collections;

@SuppressWarnings("WeakerAccess")
public class StringHelper {

    public static String repeat(String repeated, int count) {
        return String.join("", Collections.nCopies(count, repeated));
    }

    public static String getHeader(String methodName) {
        return getHeader(methodName, 73);
    }

    public static String getHeader(String methodName, int maxLength) {
        int suffixLength = Math.max(maxLength - methodName.length(), 0);
        String suffix = repeat("-", suffixLength);
        return "\n--- " + methodName + " " + suffix;
    }

}
