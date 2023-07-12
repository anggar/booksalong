package com.anggar.miniproj.booksalong.web.handler;

import com.anggar.miniproj.booksalong.data.dto.BaseResponseDto;

public abstract class ResponseBuilder {


    static BaseResponseDto<Object> createErrorBodyMessage(String message) {
        return BaseResponseDto.builder()
                .success(false)
                .data(null)
                .error(message)
                .build();
    }

}
