package com.kostylenko.common.common_http.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    private String code;
    private String message;
    private Map<String, List<String>> fieldErrors;

    public Map<String, List<String>> getFieldErrors() {
        if (isNull(fieldErrors)) {
            fieldErrors = new HashMap<>();
        }
        return fieldErrors;
    }

    public void addError(String field, String message) {
        List<String> errors = getFieldErrors().get(field);
        if (isNull(errors)) {
            errors = new ArrayList<>();
        }
        errors.add(message);
        fieldErrors.put(field, errors);
    }
}