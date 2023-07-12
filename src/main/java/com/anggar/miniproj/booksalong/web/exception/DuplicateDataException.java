package com.anggar.miniproj.booksalong.web.exception;

public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException() {
        super();
    }

    public DuplicateDataException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DuplicateDataException(final String message) {
        super(message);
    }

    public DuplicateDataException(final Throwable cause) {
        super(cause);
    }
}
