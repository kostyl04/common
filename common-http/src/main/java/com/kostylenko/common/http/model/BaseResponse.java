package com.kostylenko.common.http.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {

    private String code;
    private String message;
}