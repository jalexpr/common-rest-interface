package ru.textanalysis.common.rest.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.textanalysis.common.rest.domain.response.BaseResponse;
import ru.textanalysis.common.rest.domain.response.item.ResponseItem;
import ru.textanalysis.common.rest.exception.RestCommonRuntimeException;

@Service
public class RestClientService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public RestClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpEntity makeEntity(Object o) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity;
        try {
            httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(o), headers);
        } catch (JsonProcessingException e) {
            throw new RestCommonRuntimeException(String.format("Can't serialize request %s!", o.getClass().getName()));
        }
        return httpEntity;
    }

    private static void validateResponse(BaseResponse<? extends ResponseItem> response, String serviceName) {
        if (response == null)
            throw new RestCommonRuntimeException("Can't get response!");
        if (!response.isSuccess()) {
            throw new RestCommonRuntimeException(String.format("%s response error: \n%s",
                    serviceName, String.join("\n", response.getErrors())));
        }
    }

    public <T extends BaseResponse<? extends ResponseItem>> T post(String serviceName, String urn, Object request, Class<T> clazz) {
        String uri = String.format("%s/%s", serviceName, urn);
        HttpEntity payload = makeEntity(request);
        try {
            T response = restTemplate.postForObject(uri, payload, clazz);
            validateResponse(response, serviceName);
            return response;
        } catch (HttpClientErrorException clientException) {
            log.warn("Service response error: {}\n\nRequest to uri {} was:\n{}", clientException.getStatusCode(), uri, payloadToJson(payload));
            throw new RestCommonRuntimeException(String.format("Cannot connected to uri %s, error %s", uri, clientException.getStatusCode()));
        } catch (Throwable e) {
            throw new RestCommonRuntimeException(String.format("%s communication failed: %s -> %s\n\nRequest to uri %s was:\n%s",
                    serviceName, e.getClass().getName(), e.getMessage(), uri, payloadToJson(payload)));
        }
    }

    public String post(String uri) {
        try {
            return restTemplate.postForObject(uri, makeEntity(null), String.class);
        } catch (HttpClientErrorException clientException) {
            log.warn("Service response error: {}\n\nRequest to uri {} was:\n{}", clientException.getStatusCode(), uri, "empty");
            throw new RestCommonRuntimeException(String.format("Cannot connected to uri %s, error %s", uri, clientException.getStatusCode()));
        } catch (Throwable e) {
            throw new RestCommonRuntimeException(String.format("%s communication failed: %s -> %s\n\nRequest to uri %s was:\n%s",
                    uri, e.getClass().getName(), e.getMessage(), uri, "empty"));
        }
    }

    public <T extends BaseResponse<? extends ResponseItem>> T get(String serviceName, String urn, Class<T> clazz) {
        String uri = String.format("%s/%s", serviceName, urn);
        try {
            T response = restTemplate.getForObject(uri, clazz);
            validateResponse(response, serviceName);
            return response;
        } catch (HttpClientErrorException clientException) {
            log.warn("Service response error: {}\n\nRequest to uri {}", clientException.getStatusCode(), uri);
            throw new RestCommonRuntimeException(String.format("Cannot connected to uri %s, error %s", uri, clientException.getStatusCode()));
        } catch (Throwable e) {
            throw new RestCommonRuntimeException(String.format("%s communication failed: %s -> %s\n\nRequest to uri %s",
                    serviceName, e.getClass().getName(), e.getMessage(), uri));
        }
    }

    private String payloadToJson(HttpEntity payload) {
        try {
            return OBJECT_MAPPER.writeValueAsString(payload.getBody());
        } catch (JsonProcessingException e1) {
            throw new RestCommonRuntimeException("Failed to write HttpEntity!", e1);
        }
    }
}
