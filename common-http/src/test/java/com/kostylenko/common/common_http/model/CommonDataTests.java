package com.kostylenko.common.common_http.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(PER_CLASS)
class CommonDataTests {

    private HttpServletRequest request;

    @BeforeAll
    void init() {
        request = mock(HttpServletRequest.class);
    }

    @Test
    void langAndLocaleNonNullIfRequestHeadersAreNull() {
        when(request.getHeader(any())).thenReturn(null);
        CommonData commonData = new CommonData(request, "defaultLocale", "defaultLang");
        assertAll(
                () -> assertEquals("defaultLocale", commonData.getLocale()),
                () -> assertEquals("defaultLang", commonData.getLang())
        );
    }

    @Test
    void langAndLocaleEqualsHeadersIfRequestHeadersAreNonNull() {
        when(request.getHeader(any())).thenReturn("expected");
        CommonData commonData = new CommonData(request, "defaultLocale", "defaultLang");
        assertAll(
                () -> assertEquals("expected", commonData.getLocale()),
                () -> assertEquals("expected", commonData.getLang())
        );
    }
}
