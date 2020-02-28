package ru.textanalysis.common.rest.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import ru.textanalysis.common.rest.constants.SerialVersionUID;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseResponseAbstract<T> implements BaseResponse<T> {
    private static final long serialVersionUID = SerialVersionUID.serialVersionUID;

    @JsonProperty(value = "data", required = true)
    protected T data;

    @ApiModelProperty(value = "Время ответа")
    @JsonProperty()
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSZ")
    protected Date timestamp;

    @ApiModelProperty(value = "Статус обработки (успех/ошибка)")
    @JsonProperty(value = "success", required = true)
    protected boolean success = false;

    @ApiModelProperty(value = "Перечень ошибок")
    @JsonProperty(value = "errors")
    protected List<String> errors = new ArrayList<>();

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    public abstract void createEmptyData();
}
