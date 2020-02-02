package com.kostylenko.common.http.exception;

import lombok.Getter;
import lombok.Setter;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@Setter
public class NotFoundApiException extends ApiException {

    public NotFoundApiException(String code) {
        super(code, NOT_FOUND);
    }
}
