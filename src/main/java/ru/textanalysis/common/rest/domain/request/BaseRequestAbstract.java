package ru.textanalysis.common.rest.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import ru.textanalysis.common.rest.constants.SerialVersionUID;

public abstract class BaseRequestAbstract<T> implements BaseRequest<T> {
    private static final long serialVersionUID = SerialVersionUID.serialVersionUID;

    @ApiModelProperty(value = "Id текущей транзакции")
    @JsonProperty(value = "tid", required = true)
    protected Long tid;

    @Override
    public Long getTid() {
        return tid;
    }

    @Override
    public void setTid(Long tid) {
        this.tid = tid;
    }
}
