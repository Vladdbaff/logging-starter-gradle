package ru.zhdanov.loggingstartergradle.dto.common;

import jakarta.validation.Valid;

public record CommonRequest<T>(

        @Valid
        T body
) {
}
