package ru.textanalysis.common.rest.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;

public final class ErrorHelper {
    public static String shortStr(Throwable e) {
        return String.format("%s -> %s", e.getClass().getSimpleName(), e.getMessage());
    }

    public static String longStr(Throwable e) {
        return ExceptionUtils.getStackTrace(e);
    }
}
