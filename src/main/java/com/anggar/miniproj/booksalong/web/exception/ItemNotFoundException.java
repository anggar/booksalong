package com.anggar.miniproj.booksalong.web.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ItemNotFoundException(final String message) {
        super(message);
    }

    public ItemNotFoundException(final Throwable cause) {
        super(cause);
    }
}