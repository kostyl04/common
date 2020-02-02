package com.kostylenko.common.common_http.exception;

import lombok.Getter;
import lombok.Setter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@Setter
public class BadRequestApiException extends ApiException {

    public BadRequestApiException(String code) {
        super(code, BAD_REQUEST);
    }
}
