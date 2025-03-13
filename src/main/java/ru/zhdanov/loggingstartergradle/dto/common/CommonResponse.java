package ru.zhdanov.loggingstartergradle.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonResponse<T> (
        UUID id,
        T body,
        String errorMessage,
        List<ValidationError> validationErrors
) {

    public CommonResponse() {
        this(UUID.randomUUID(), null, null, null);
    }

    public CommonResponse(T body) {
        this(UUID.randomUUID(), body, null, null);
    }

    public CommonResponse(String errorMessage) {
        this(UUID.randomUUID(), null, errorMessage, null);
    }

    public CommonResponse(String errorMessage, List<ValidationError> validationErrors) {
        this(UUID.randomUUID(), null, errorMessage, validationErrors);
    }
}
