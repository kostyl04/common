package com.kostylenko.common.common_http.model;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

import static com.kostylenko.common.common_http.utils.Constant.*;
import static java.util.Objects.nonNull;
import static java.util.UUID.randomUUID;

@Getter
public class CommonData {

    private final String flowId;
    private final String client;
    private final String locale;
    private final String lang;

    public CommonData(HttpServletRequest request, String defaultLocale, String defaultLang) {
        flowId = randomUUID().toString();
        client = request.getHeader(CLIENT_HEADER);
        locale = nonNull(request.getHeader(LOCALE_HEADER)) ? request.getHeader(LOCALE_HEADER) : defaultLocale;
        lang = nonNull(request.getHeader(LANG_HEADER)) ? request.getHeader(LANG_HEADER) : defaultLang;
    }
}