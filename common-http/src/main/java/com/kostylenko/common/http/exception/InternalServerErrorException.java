package com.kostylenko.common.http.exception;

import lombok.Getter;
import lombok.Setter;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@Setter
public class InternalServerErrorException extends ApiException {

    public InternalServerErrorException(String code) {
        super(code, INTERNAL_SERVER_ERROR);
    }
}
