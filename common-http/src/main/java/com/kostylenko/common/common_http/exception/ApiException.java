package com.kostylenko.common.common_http.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Getter
public class ApiException extends RuntimeException {

    protected String code;
    protected final HttpStatus status;
    protected Map<String, Object> arguments;

    ApiException(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
        this.arguments = new HashMap<>();
    }

    public ApiException addArgument(String name, Object argument) {
        arguments.put(name, argument);
        return this;
    }

    public boolean hasArguments() {
        return isNotEmpty(arguments);
    }
}