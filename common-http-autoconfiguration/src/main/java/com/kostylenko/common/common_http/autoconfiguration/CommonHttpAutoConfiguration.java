package com.kostylenko.common.common_http.autoconfiguration;

import com.kostylenko.common.common_http.exception.handler.GlobalExceptionHandler;
import com.kostylenko.common.common_http.exception.message.TemplateSource;
import com.kostylenko.common.common_http.exception.message.TemplateSourceMock;
import com.kostylenko.common.common_http.exception.template.TemplateProcessor;
import com.kostylenko.common.common_http.exception.template.VelocityTemplateProcessor;
import com.kostylenko.common.common_http.model.CommonData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Configuration
public class CommonHttpAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TemplateProcessor.class)
    public TemplateProcessor templateProcessor() {
        return new VelocityTemplateProcessor();


    //TODO Config-service template source
    @Bean
    public TemplateSource templateSource() {
        return new TemplateSourceMock();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = TARGET_CLASS)
    @ConditionalOnMissingBean(CommonData.class)
    public CommonData commonData(HttpServletRequest request) {
        return new CommonData(request, "ru", "ru");
    }

    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler(CommonData commonData) {
        return new GlobalExceptionHandler(templateProcessor(), templateSource(), commonData);
    }
}