package com.anggar.miniproj.booksalong.web.exception;

import com.anggar.miniproj.booksalong.data.entity.BaseCompositeEntity;
import com.anggar.miniproj.booksalong.data.entity.BaseSingleEntity;
import jakarta.persistence.Entity;
import lombok.Getter;

public class ItemNotFoundException extends RuntimeException implements CustomDataError {

    private record Data (
            String entity
    ) {}

    @Getter
    private final Data customData;

    public ItemNotFoundException(final Class<?> cls) {
        super(cls.getSimpleName() + " not found.");
        this.customData = new Data(cls.getSimpleName());
    }
}
