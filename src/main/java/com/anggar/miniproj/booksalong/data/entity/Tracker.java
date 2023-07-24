package com.anggar.miniproj.booksalong.data.entity;

import com.anggar.miniproj.booksalong.data.entity.enums.TrackingStateEnum;
import com.anggar.miniproj.booksalong.data.entity.idclass.TrackerId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TrackerId.class)
@Table(name = "tracker")
public class Tracker extends BaseCompositeEntity {

    @Getter
    @Setter
    @Column
    private long currentPage;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column
    private TrackingStateEnum state;

    @Getter
    @Setter
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Getter
    @Setter
    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

}