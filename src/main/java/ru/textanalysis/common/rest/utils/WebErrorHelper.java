package ru.textanalysis.common.rest.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.textanalysis.common.rest.domain.response.DataResponse;

public final class WebErrorHelper {
    private static final Logger log = LoggerFactory.getLogger(WebErrorHelper.class);

    public static ResponseEntity<String> handleAnyErrorSync(Throwable e) {
        log.error(ExceptionUtils.getStackTrace(e));
        DataResponse result = new DataResponse();
        result.getErrors().add(ErrorHelper.shortStr(e));
        return WebHelper.makeResult(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
