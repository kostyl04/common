package com.kostylenko.common.common_http.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.kostylenko.common.common_http.utils.Constant.CLIENT_HEADER;
import static java.util.UUID.randomUUID;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Getter
@Setter
@Component
@Scope(proxyMode = TARGET_CLASS, value = SCOPE_REQUEST)
public class CommonData {

    private String flowId;
    private String client;

    public CommonData(HttpServletRequest request) {
        flowId = randomUUID().toString();
        client = request.getHeader(CLIENT_HEADER);
    }
}