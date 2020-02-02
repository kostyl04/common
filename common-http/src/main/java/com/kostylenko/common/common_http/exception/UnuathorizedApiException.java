package com.kostylenko.common.common_http.exception;

import lombok.Getter;
import lombok.Setter;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@Setter
public class UnuathorizedApiException extends ApiException {

    public UnuathorizedApiException(String code) {
        super(code, UNAUTHORIZED);
    }
}
