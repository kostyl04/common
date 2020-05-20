package com.kostylenko.common.common_http.autoconfiguration;

import com.kostylenko.common.common_http.exception.FieldErrorMessageProcessor;
import com.kostylenko.common.common_http.exception.NotBlankMessageProcessor;
import com.kostylenko.common.common_http.exception.NotEmptyMessageProcessor;
import com.kostylenko.common.common_http.exception.PatternMessageProcessor;
import com.kostylenko.common.common_http.exception.handler.GlobalExceptionHandler;
import com.kostylenko.common.common_http.exception.message.TemplateSource;
import com.kostylenko.common.common_http.exception.template.TemplateProcessor;
import com.kostylenko.common.common_http.exception.template.VelocityTemplateProcessor;
import com.kostylenko.common.common_http.model.CommonData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Configuration
public class CommonHttpAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TemplateProcessor.class)
    public TemplateProcessor templateProcessor() {
        return new VelocityTemplateProcessor();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = TARGET_CLASS)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @ConditionalOnMissingBean(CommonData.class)
    public CommonData commonData(HttpServletRequest request) {
        return new CommonData(request, "en", "en");
    }

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler(CommonData commonData, TemplateSource templateSource) {
        return new GlobalExceptionHandler(fieldErrorMessageProcessors(templateSource), templateProcessor(), templateSource, commonData);
    }

    private Set<FieldErrorMessageProcessor> fieldErrorMessageProcessors(TemplateSource templateSource) {
        HashSet<FieldErrorMessageProcessor> processors = new HashSet<>();
        processors.add(new PatternMessageProcessor(templateSource));
        processors.add(new NotBlankMessageProcessor(templateSource));
        processors.add(new NotEmptyMessageProcessor(templateSource));
        return processors;
    }
}