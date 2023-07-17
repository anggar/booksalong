package com.anggar.miniproj.booksalong.web.exception;

public sealed abstract class BaseBadDataException extends RuntimeException
        permits DuplicateDataException, IdMismatchException
{

    public BaseBadDataException(String msg) {
        super(msg);
    }
}
