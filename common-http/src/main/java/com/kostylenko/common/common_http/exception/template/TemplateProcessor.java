package com.kostylenko.common.common_http.exception.template;

import java.util.Map;

public interface TemplateProcessor {

    String process(String template, Map<String, Object> args) throws TemplateProcessingException;

    String process(String template, Map<String, Object> args, String defaultValue);
}
