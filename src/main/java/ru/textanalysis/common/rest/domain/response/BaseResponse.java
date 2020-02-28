package ru.textanalysis.common.rest.domain.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface BaseResponse<T> extends Serializable {
    Date getTimestamp();

    void setTimestamp(Date timestamp);

    boolean isSuccess();

    void setSuccess(boolean success);

    List<String> getErrors();

    void setErrors(List<String> errors);

    T getData();

    void setData(T data);
}
