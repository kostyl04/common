package com.kostylenko.common.common_http.exception;

public interface FieldErrorMessageProcessor {

    String getCodeDescription(String name, String code, String lang);

    boolean supports(String code);
}
