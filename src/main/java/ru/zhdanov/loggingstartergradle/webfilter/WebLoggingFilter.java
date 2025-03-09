package ru.zhdanov.loggingstartergradle.webfilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ru.zhdanov.loggingstartergradle.utils.HttpUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class WebLoggingFilter extends HttpFilter {

    private static final Logger log = LoggerFactory.getLogger(WebLoggingFilter.class);


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        MaskedHeaderRequestWrapper requestWrapper = new MaskedHeaderRequestWrapper(request);

        String method = requestWrapper.getMethod();
        String requestURI = requestWrapper.getRequestURI() + HttpUtils.formatQueryString(requestWrapper);
        String headers = HttpUtils.inlineHeaders(requestWrapper);

        log.info("Запрос: {} {} {}", method, requestURI, headers);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            super.doFilter(requestWrapper, responseWrapper, chain);

            String responseBody = "body={" + new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8) + "}";
            log.info("Ответ: {} {} {} {}", method, requestURI, response.getStatus(), responseBody);
        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }
}
