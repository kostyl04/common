package com.kostylenko.common.common_http.exception.template;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class VelocityTemplateProcessorTests {

    private TemplateProcessor templateProcessor = new VelocityTemplateProcessor();
    private static final String PRE_PROCESSED_STRING = "$arg1 + $arg2 = $arg3";
    private static final String TEMPLATE_WITH_ERROR = "$if($arg1 > 0)yes#end";

    private static final String PROCESSED_STRING = "1 + 1 = 2";

    @Test
    void shouldReturnProcessedString_TemplateAndArgsAreGood() throws TemplateProcessingException {
        Map<String, Object> args = Map.of("arg1", 1, "arg2", 1, "arg3", 2);
        String processed = templateProcessor.process(PRE_PROCESSED_STRING, args);
        assertEquals(PROCESSED_STRING, processed);
    }

    @Test
    void shouldThrowTemplateProcessingException() {
        TemplateProcessingException exception = assertThrows(TemplateProcessingException.class, () -> {
            templateProcessor.process(TEMPLATE_WITH_ERROR, Map.of("arg1", true));
        });
        assertEquals(exception.getMessage(), "Error processing template: " + TEMPLATE_WITH_ERROR);
    }

    @Test
    void shouldReturnDefaultValue_IfThrowTemplateProcessingException() {
        templateProcessor.process(TEMPLATE_WITH_ERROR, Map.of("arg1", true), "Default");
        assertEquals("Default", "Default");
    }
}
