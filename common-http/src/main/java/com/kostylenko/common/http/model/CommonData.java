package com.kostylenko.common.http.model;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

import static com.kostylenko.common.http.utils.Constant.CLIENT_HEADER;
import static java.util.UUID.randomUUID;

@Getter
@Setter
public class CommonData {

    private String flowId;
    private String client;

    public CommonData(HttpServletRequest request) {
        flowId = randomUUID().toString();
        client = request.getHeader(CLIENT_HEADER);
    }
}