package com.anggar.miniproj.booksalong.web.handler;

import com.anggar.miniproj.booksalong.web.exception.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.AbstractMap;

import static com.anggar.miniproj.booksalong.web.handler.ResponseBuilder.createErrorBodyMessage;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(ItemNotFoundException ex, WebRequest request) {
        var body = createErrorBodyMessage(ex.getMessage(), ex.getCustomData());

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BaseBadDataException.class)
    public ResponseEntity<Object> handleAppBadRequest(Exception ex, WebRequest request) {
        var body = createErrorBodyMessage(ex.getMessage());

        if (ex instanceof CustomDataError) {
            body = createErrorBodyMessage(ex.getMessage(), ((CustomDataError) ex).getCustomData());
        }

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler({
            ConstraintViolationException.class,
            DataIntegrityViolationException.class,
    })
    public ResponseEntity<Object> handleDatabaseException(Exception ex, WebRequest request) {
        var errorData = new AbstractMap.SimpleEntry<>("detail", ex.getMessage());
        var body = createErrorBodyMessage("Database integrity violation.", errorData);

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        var body = createErrorBodyMessage("Illegal state exception.", ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({
            UsernameNotFoundException.class,
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        var body = createErrorBodyMessage(ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
