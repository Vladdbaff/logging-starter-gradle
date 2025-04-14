package ru.zhdanov.loggingstartergradle.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zhdanov.loggingstartergradle.properties.MaskingHeaderProperties;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HttpUtils {

    @Autowired
    private MaskingHeaderProperties properties;

    public String inlineHeaders(HttpServletRequest request) {
        Map<String, Collection<String>> headerMap = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(it -> it, headerName -> Collections.list(request.getHeaders(headerName))));

        return inlineHeaders(headerMap);
    }

    public String inlineHeaders(Map<String, Collection<String>> headersMap) {
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
