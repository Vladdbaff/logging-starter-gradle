package ru.zhdanov.loggingstartergradle.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "logging.header")
public record MaskingHeaderProperties(
        Set<String> names
) {
}
