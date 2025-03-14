package ru.zhdanov.loggingstartergradle.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import ru.zhdanov.loggingstartergradle.properties.MaskingHeaderProperties;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpUtils {

    public static String inlineHeaders(HttpServletRequest request, MaskingHeaderProperties properties) {
        Map<String, String> headerMap = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(it -> it, request::getHeader));

        String headers = headerMap.entrySet().stream()
                .map(entry -> {
                    String headerName = entry.getKey();
                    String headerValue = properties.names().contains(headerName)? "****" : entry.getValue();

                    return headerName + "=" + headerValue;
                })
                .collect(Collectors.joining(","));

        return "headers={" + headers + "}";
    }

    public static String formatQueryString(HttpServletRequest request) {
        return Optional.ofNullable(request.getQueryString())
                .map(qs -> "?" + qs)
                .orElse(Strings.EMPTY);
    }
}
