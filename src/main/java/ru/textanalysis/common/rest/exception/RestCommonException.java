package ru.textanalysis.common.rest.exception;

import ru.textanalysis.common.exception.RuTextanalysisException;

public class RestCommonException extends RuTextanalysisException {
    public RestCommonException() {
    }

    public RestCommonException(String s) {
        super(s);
    }

    public RestCommonException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RestCommonException(Throwable throwable) {
        super(throwable);
    }
}
