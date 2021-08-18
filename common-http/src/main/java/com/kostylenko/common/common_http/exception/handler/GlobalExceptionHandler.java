package com.kostylenko.common.common_http.exception.handler;

import com.kostylenko.common.common_http.exception.ApiException;
import com.kostylenko.common.common_http.exception.processor.FieldErrorMessageProcessor;
import com.kostylenko.common.common_http.exception.InternalServerErrorException;
import com.kostylenko.common.common_http.exception.message.TemplateSource;
import com.kostylenko.common.common_http.exception.template.TemplateProcessor;
import com.kostylenko.common.common_http.model.BaseResponse;
import com.kostylenko.common.common_http.model.CommonData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Set<FieldErrorMessageProcessor> fieldErrorMessageProcessors;
    private TemplateProcessor templateProcessor;
    private TemplateSource templateSource;
    private CommonData commonData;
    private static final String baseResponseCode = "validation.error";

    public GlobalExceptionHandler(TemplateProcessor templateProcessor, TemplateSource templateSource) {
        this.templateProcessor = templateProcessor;
        this.templateSource = templateSource;
    }

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        BaseResponse baseResponse = new BaseResponse();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String code = fieldError.getCode();
            FieldErrorMessageProcessor fieldErrorMessageProcessor = fieldErrorMessageProcessors.stream()
                    .filter(processor -> processor.supports(code))
                    .findFirst().orElseThrow(() -> new RuntimeException("No such processor found"));
            String messageDescription = fieldErrorMessageProcessor.getCodeDescription(fieldName, code, "en");
            baseResponse.addError(fieldName, messageDescription);
        });
        baseResponse.setCode(baseResponseCode);
        return new ResponseEntity<>(baseResponse, BAD_REQUEST);
    }

    private ResponseEntity<BaseResponse> buildResponse(ApiException apiException, String message) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(apiException.getCode());
        baseResponse.setMessage(message);
        return new ResponseEntity<>(baseResponse, apiException.getStatus());
    }
}
