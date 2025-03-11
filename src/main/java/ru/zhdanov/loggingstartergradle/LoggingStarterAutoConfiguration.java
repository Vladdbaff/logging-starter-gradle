package ru.zhdanov.loggingstartergradle;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import ru.zhdanov.loggingstartergradle.aspect.LogExecutionAspect;
import ru.zhdanov.loggingstartergradle.webfilter.WebLoggingFilter;
import ru.zhdanov.loggingstartergradle.webfilter.WebLoggingRequestBodyAdvice;

@AutoConfiguration
@ConfigurationPropertiesScan(basePackages = "ru.zhdanov.loggingstartergradle.properties")
@ConditionalOnProperty(prefix = "logging", value = "enabled", havingValue = "true", matchIfMissing = true)
public class LoggingStarterAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "logging", value = "log-exec-time", havingValue = "true")
    public LogExecutionAspect logExecutionAspect() {
        return new LogExecutionAspect();
    }

    @Bean
    @ConditionalOnProperty(prefix = "logging.web-logging", value = "enabled", havingValue = "true", matchIfMissing = true)
    public WebLoggingFilter webLoggingFilter() {
        return new WebLoggingFilter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "logging.web-logging", value = {"enabled", "log-body"}, havingValue = "true")
    public WebLoggingRequestBodyAdvice webLoggingRequestBodyAdvice() {
        return new WebLoggingRequestBodyAdvice();
    }
}
