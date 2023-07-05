package com.anggar.miniproj.booksalong.web.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.anggar.miniproj.booksalong.data.dto.BaseResponseDto;

@ControllerAdvice
public class BaseResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    private final Logger logger = LoggerFactory.getLogger(BaseResponseControllerAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {

        if ( body == null || body instanceof BaseResponseDto) {
            return body;
        }

        if (body instanceof ProblemDetail) {
            var problemDetail = (ProblemDetail) body;
            
           return BaseResponseDto.builder()
                .data(new ProblemDetailResponseData(problemDetail))
                .success(false)
                .error(problemDetail.getTitle())
                .build();
        }

        logger.debug(body.getClass().getName());

        return BaseResponseDto.builder()
            .success(true)
            .data(body)
            .error(null)
            .build();
    }

    private record ProblemDetailResponseData (
        String detail,
        URI instance,
        int status        
    ) {
        public ProblemDetailResponseData(ProblemDetail problemDetail) {
            this(problemDetail.getDetail(), problemDetail.getInstance(), problemDetail.getStatus());
        }
    }
}
