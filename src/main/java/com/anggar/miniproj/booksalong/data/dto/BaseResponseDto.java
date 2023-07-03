package com.anggar.miniproj.booksalong.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class BaseResponseDto<T> {

    @Getter
    @Setter
    private boolean success;

    @Getter
    @Setter
    private T data;

    @Getter
    @Setter
    private String error;
}
