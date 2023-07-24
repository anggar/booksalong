package com.anggar.miniproj.booksalong.web.exception;

import com.anggar.miniproj.booksalong.data.entity.BaseSingleEntity;
import lombok.Getter;

public final class DuplicateDataException extends BaseBadDataException implements CustomDataError {

    private static final String GENERAL_MSG = "Duplicated data found.";

    private record Data (
            String entity,
            String field
    ) { }

    @Getter
    private final Data customData;

    public DuplicateDataException(Class<? extends BaseSingleEntity> cls) {
        super(GENERAL_MSG);
        this.customData = new Data(cls.getSimpleName(), null);
    }

    public DuplicateDataException(Class<? extends BaseSingleEntity> cls, String field) {
        super(GENERAL_MSG);
        this.customData = new Data(cls.getSimpleName(), field);
    }
}
