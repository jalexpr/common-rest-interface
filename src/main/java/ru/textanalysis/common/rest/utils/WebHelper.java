package ru.textanalysis.common.rest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.textanalysis.common.rest.domain.response.BaseResponse;

import java.util.Date;

public final class WebHelper {
    private final static Logger log = LoggerFactory.getLogger(WebHelper.class);

    public static <T> ResponseEntity<String> makeSuccessResult(BaseResponse<T> data) {
        return makeSuccessResult(data, null);
    }

    public static <T> ResponseEntity<String> makeSuccessResult(BaseResponse<T> data, ObjectMapper mapper) {
        return makeResult(data, mapper, HttpStatus.OK);
    }

    public static <T> ResponseEntity<String> makeResultForbidden(BaseResponse<T> data) {
        return makeResult(data, HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<String> makeResult(BaseResponse<T> data, HttpStatus status) {
        return makeResult(data, null, status);
    }

    public static <T> ResponseEntity<String> makeResult(BaseResponse<T> data, ObjectMapper mapper, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        data.setTimestamp(new Date());
        return new ResponseEntity<>(JsonHelper.writeAsString(data, mapper), headers, status);
    }
}
