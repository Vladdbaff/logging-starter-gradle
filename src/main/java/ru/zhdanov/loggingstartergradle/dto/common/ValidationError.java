package ru.zhdanov.loggingstartergradle.dto.common;

public record ValidationError (
        String field,
        String message
) {

}
