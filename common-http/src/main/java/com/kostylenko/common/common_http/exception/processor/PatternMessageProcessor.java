package com.kostylenko.common.common_http.exception.processor;

import com.kostylenko.common.common_http.exception.message.TemplateSource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatternMessageProcessor implements FieldErrorMessageProcessor {

    private TemplateSource templateSource;
    private static final String CODE = "validation.pattern.";

    @Override
    public String getCodeDescription(String name, String code, String lang) {
        return templateSource.getTemplate(lang, CODE + name);
    }

    @Override
    public boolean supports(String code) {
        return "Pattern".equalsIgnoreCase(code);
    }
}
