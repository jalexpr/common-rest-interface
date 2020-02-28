package ru.textanalysis.common.rest.classes;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class ServiceWorksResult<T> {
    private final T data;
    private final List<String> errorMessages;

    public ServiceWorksResult(T data) {
        this.data = data;
        this.errorMessages = new LinkedList<>();
    }

    public ServiceWorksResult(T data, String errorMessage) {
        this(data);
        if (StringUtils.isNoneBlank(errorMessage)) {
            this.errorMessages.add(errorMessage);
        }
    }

    public ServiceWorksResult(T data, List<String> errorMessages) {
        this(data);
        if (!errorMessages.isEmpty()) {
            this.errorMessages.addAll(errorMessages);
        }
    }

    public T getResult() {
        return data;
    }

    public List<String> getErrorMessage() {
        return errorMessages;
    }
}
