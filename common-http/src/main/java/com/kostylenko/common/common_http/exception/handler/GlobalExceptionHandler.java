package com.kostylenko.common.common_http.exception.handler;

import com.kostylenko.common.common_http.exception.ApiException;
import com.kostylenko.common.common_http.exception.InternalServerErrorException;
import com.kostylenko.common.common_http.exception.message.TemplateSource;
import com.kostylenko.common.common_http.exception.template.TemplateProcessor;
import com.kostylenko.common.common_http.model.BaseResponse;
import com.kostylenko.common.common_http.model.CommonData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private TemplateProcessor templateProcessor;
    private TemplateSource templateSource;
    private CommonData commonData;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<BaseResponse> handle(ApiException exception) {
        String template = templateSource.getTemplate(commonData.getLang(), exception.getCode());
        String processedTemplate = templateProcessor.process(template, exception.getArguments(), exception.getCode());
        return buildResponse(exception, processedTemplate);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseResponse> handle(Throwable throwable) {
        log.error("Unexpected error, cause: ", throwable);
        InternalServerErrorException internalServerErrorException = new InternalServerErrorException();
        return handle(internalServerErrorException);
    }

    private ResponseEntity<BaseResponse> buildResponse(ApiException apiException, String message) {
        BaseResponse baseResponse = new BaseResponse(apiException.getCode(), message);
        return new ResponseEntity<>(baseResponse, apiException.getStatus());
    }
}