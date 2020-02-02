package com.kostylenko.common.http.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@Setter
public class ForbiddenApiException extends ApiException {

    public ForbiddenApiException(String code) {
        super(code, FORBIDDEN);
    }
}
