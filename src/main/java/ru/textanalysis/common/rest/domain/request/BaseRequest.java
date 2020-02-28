package ru.textanalysis.common.rest.domain.request;

import java.io.Serializable;

public interface BaseRequest<T> extends Serializable {
    Long getTid();

    void setTid(Long tid);
}
