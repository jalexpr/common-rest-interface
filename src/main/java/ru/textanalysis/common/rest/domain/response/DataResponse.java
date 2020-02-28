package ru.textanalysis.common.rest.domain.response;

import java.util.HashMap;
import java.util.Map;

public class DataResponse extends BaseResponseAbstract<Map<String, Object>> {
    @Override
    public void createEmptyData() {
        this.data = new HashMap<>();
    }
}
