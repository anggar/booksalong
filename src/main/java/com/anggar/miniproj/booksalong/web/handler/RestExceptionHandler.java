package com.anggar.miniproj.booksalong.web.handler;

import com.anggar.miniproj.booksalong.web.exception.DuplicateDataException;
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

import static com.anggar.miniproj.booksalong.web.handler.ResponseBuilder.createErrorBodyMessage;


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
            DuplicateDataException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class,
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        var body = createErrorBodyMessage(ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
