package com.anggar.miniproj.booksalong.data.entity.idclass;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TrackerId implements Serializable {

    @Getter
    @Setter
    private long user;

    @Getter
    @Setter
    private long book;
}
