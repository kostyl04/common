package com.kostylenko.common.common_http.autoconfiguration;

import com.kostylenko.common.common_http.exception.handler.GlobalExceptionHandler;
import com.kostylenko.common.common_http.exception.message.TemplateSource;
import com.kostylenko.common.common_http.exception.template.TemplateProcessor;
import com.kostylenko.common.common_http.exception.template.VelocityTemplateProcessor;
import com.kostylenko.common.common_http.model.CommonData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class CommonHttpAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TemplateProcessor.class)
    public TemplateProcessor templateProcessor() {
        return new VelocityTemplateProcessor();
    }

    //TODO Config-service template source
    @Bean
    @ConditionalOnMissingBean(TemplateProcessor.class)
    public TemplateSource templateSource() {
        return (lang, code) -> "Some template";
    }

    @Bean
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