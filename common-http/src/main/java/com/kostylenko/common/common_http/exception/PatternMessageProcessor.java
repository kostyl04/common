package com.kostylenko.common.common_http.exception;

import com.kostylenko.common.common_http.exception.message.TemplateSource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatternMessageProcessor implements FieldErrorMessageProcessor {

    private TemplateSource templateSource;

    @Override
    public String getCodeDescription(String name, String code, String lang) {
        return templateSource.getTemplate(lang, "validation." + code + "." + name);
    }

    @Override
    public boolean supports(String code) {
        return "Pattern".equalsIgnoreCase(code);
    }
}
