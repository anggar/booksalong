package com.anggar.miniproj.booksalong.web.exception;

public final class IdMismatchException extends BaseBadDataException {

    public IdMismatchException() {
        super("ID supplied is not the same.");
    }

}
