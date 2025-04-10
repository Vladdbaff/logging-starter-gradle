package ru.zhdanov.loggingstartergradle.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import ru.zhdanov.loggingstartergradle.properties.MaskingHeaderProperties;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpUtils {

    public static String inlineHeaders(HttpServletRequest request, MaskingHeaderProperties properties) {
        Map<String, Collection<String>> headerMap = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(it -> it, headerName -> Collections.list(request.getHeaders(headerName))));

        return inlineHeaders(headerMap, properties);
    }

    public static String inlineHeaders(Map<String, Collection<String>> headersMap, MaskingHeaderProperties properties) {
        String headers = headersMap.entrySet().stream()
                .map(entry -> {
                    String headerName = entry.getKey();
                    String headerValue = properties.names().contains(headerName)? "****" : String.join(",", entry.getValue());

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
