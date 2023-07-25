package com.anggar.miniproj.booksalong.data.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// make no assumption for ID column
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseCompositeEntity extends BaseEntity {

    @Getter
    @Setter
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
