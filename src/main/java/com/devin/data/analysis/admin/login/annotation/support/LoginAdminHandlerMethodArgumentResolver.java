package com.devin.data.analysis.admin.login.annotation.support;

import com.devin.data.analysis.admin.login.annotation.LoginAdmin;
import com.devin.data.analysis.admin.login.biz.impl.AdminTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class LoginAdminHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String LOGIN_TOKEN_KEY = "X-Token";

    private final Logger logger = LoggerFactory.getLogger(LoginAdminHandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(String.class) && parameter.hasParameterAnnotation(LoginAdmin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {

        String token = request.getHeader(LOGIN_TOKEN_KEY);
        logger.debug("hkh token is {}", token);
        if (token == null || token.isEmpty()) {
            return null;
        }

        return AdminTokenManager.getIdDaAdmin(token);
    }
}
