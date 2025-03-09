package ru.zhdanov.loggingstartergradle.webfilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class MaskedHeaderRequestWrapper extends HttpServletRequestWrapper {

    private static final Set<String> SENSITIVE_HEADERS = new HashSet<>(Arrays.asList(
            "authorization", "cookie", "password"
    ));

    public MaskedHeaderRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = super.getHeader(name);
        return (SENSITIVE_HEADERS.contains(name)) ? "****" : headerValue;
    }
}
