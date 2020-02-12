package com.kostylenko.common.common_http.exception.template;

public class TemplateProcessingException extends Exception {

    public TemplateProcessingException(String message) {
        super(message);
    }

    public TemplateProcessingException(String message, Throwable cause) {
        super(message, cause);
    }


}
