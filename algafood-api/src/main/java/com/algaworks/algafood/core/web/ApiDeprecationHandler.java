package com.algaworks.algafood.core.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiDeprecationHandler  extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getRequestURI().startsWith("/v1")) {
            response.addHeader("X-Algafood-Deprecated", "Versão depreciada da API Algafood");
        }

        return true;
    }
}
