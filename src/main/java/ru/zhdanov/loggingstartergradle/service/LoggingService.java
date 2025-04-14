package ru.zhdanov.loggingstartergradle.service;

import feign.Request;
import feign.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhdanov.loggingstartergradle.dto.RequestDirection;
import ru.zhdanov.loggingstartergradle.utils.HttpUtils;

import java.nio.charset.StandardCharsets;

@Service
public class LoggingService {

    @Autowired
    private HttpUtils httpUtils;

    private static final Logger log = LoggerFactory.getLogger(LoggingService.class);

    public void logRequest(HttpServletRequest request) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI() + HttpUtils.formatQueryString(request);
        String headers = httpUtils.inlineHeaders(request);

        log.info("Запрос: {} {} {} {}", RequestDirection.IN, method, requestURI, headers);
    }

    public void logFeignRequest(Request request) {
        String method = request.httpMethod().name();
        String requestURI = request.url();
        String headers = httpUtils.inlineHeaders(request.headers());
        String body = new String(request.body(), StandardCharsets.UTF_8);

        log.info("Запрос: {} {} {} {} body={}", RequestDirection.OUT, method, requestURI, headers, body);
    }

    public void logRequestBody(HttpServletRequest request, Object body) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI() + HttpUtils.formatQueryString(request);

        log.info("Тело запроса: {}, {} {} {}", RequestDirection.IN, method, requestURI, body);
    }

    public void logResponse(HttpServletRequest request, HttpServletResponse response, String responseBody) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI() + HttpUtils.formatQueryString(request);

        log.info("Ответ: {} {} {} {} body={}", RequestDirection.OUT, method, requestURI, response.getStatus(), responseBody);
    }

    public void logFeignResponse(Response response, String responseBody) {
        String method = response.request().httpMethod().name();
        String requestURI = response.request().url();
        int status = response.status();

        log.info("Ответ: {} {} {} {} body={}", RequestDirection.OUT, method, requestURI, status, responseBody);
    }
}
