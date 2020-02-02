package com.kostylenko.common.common_http.exception;

import lombok.Getter;
import lombok.Setter;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
@Setter
public class ForbiddenApiException extends ApiException {

    public ForbiddenApiException(String code) {
        super(code, FORBIDDEN);
    }
}
