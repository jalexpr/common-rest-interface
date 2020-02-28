package ru.textanalysis.common.rest.exception;

import ru.textanalysis.common.exception.RuTextanalysisRuntimeException;

public class RestCommonRuntimeException extends RuTextanalysisRuntimeException {
    public RestCommonRuntimeException() {
    }

    public RestCommonRuntimeException(String s) {
        super(s);
    }

    public RestCommonRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RestCommonRuntimeException(Throwable throwable) {
        super(throwable);
    }

}
