package com.kostylenko.common.common_http.exception.message;

public class TemplateSourceMock implements TemplateSource {

    @Override
    public String getTemplate(String lang, String code) {
        return "some template";
    }
}
