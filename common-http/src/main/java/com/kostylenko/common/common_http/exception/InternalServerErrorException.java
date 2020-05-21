package com.kostylenko.common.common_http.exception;

import lombok.Getter;
import lombok.Setter;

import static com.kostylenko.common.common_http.utils.Constant.INTERNAL_SERVER_ERROR_MESSAGE_CODE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@Setter
public class InternalServerErrorException extends ApiException {

    private String code;

    public InternalServerErrorException(String code) {
        super(code, INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException() {
        super(INTERNAL_SERVER_ERROR_MESSAGE_CODE, INTERNAL_SERVER_ERROR);
    }
}
