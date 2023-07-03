package com.anggar.miniproj.booksalong.web.exception;

public class IdMismatchException extends RuntimeException {

    public IdMismatchException() {
        super();
    }

    public IdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IdMismatchException(final String message) {
        super(message);
    }

    public IdMismatchException(final Throwable cause) {
        super(cause);
    }
}
