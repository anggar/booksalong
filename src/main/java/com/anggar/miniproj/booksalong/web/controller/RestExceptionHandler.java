package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.data.dto.BaseResponseDto;
import com.anggar.miniproj.booksalong.web.exception.IdMismatchException;
import com.anggar.miniproj.booksalong.web.exception.ItemNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ItemNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        var errorMsg = ex.getMessage();
        if (errorMsg == null || errorMsg.isEmpty()) {
            errorMsg = "Item not found";
        }

        var body = createErrorBodyMessage(errorMsg);

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({
            IdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class,
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        var body = createErrorBodyMessage(ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private BaseResponseDto<Object> createErrorBodyMessage(String message) {
        return BaseResponseDto.builder()
            .success(false)
            .data(null)
            .error(message)
            .build();
    }
}
