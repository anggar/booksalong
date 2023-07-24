package com.anggar.miniproj.booksalong.data.dto;

import com.anggar.miniproj.booksalong.data.entity.Book;
import com.anggar.miniproj.booksalong.data.entity.Tracker;
import com.anggar.miniproj.booksalong.data.entity.enums.TrackingStateEnum;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public abstract class TrackerDto {
    public sealed interface Data permits Data.Compact, Data.WithBook {
        record Compact (
                TrackingStateEnum state,
                Long currentPageNumbers,
                Long totalPageNumbers,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) implements Data {
            public Compact(Tracker tracker, Book book) {
                this(tracker.getState(), tracker.getCurrentPage(), book.getPageNumbers(),
                        tracker.getCreatedAt(), tracker.getUpdatedAt()
                );

            }
        }

        record WithBook (
                TrackingStateEnum state,
                Long currentPageNumbers,
                Long totalPageNumbers,
                BookDto.Data.Compact book,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) implements Data {
            public WithBook(Tracker tracker, Book book) {
                this(tracker.getState(), tracker.getCurrentPage(),
                        book.getPageNumbers(), new BookDto.Data.Compact(book),
                        tracker.getCreatedAt(), tracker.getUpdatedAt()
                );

            }
        }    }

    public record TrackRequest (
            @Min(1)
            long bookId,
            // TODO: validate by enum
            String actionState,
            long currentPage
    ) {}

    @AllArgsConstructor
    public static class SingleTracker<T extends TrackerDto.Data> {
        @Getter
        private T tracker;

        public static <K extends Data> TrackerDto.SingleTracker<? extends TrackerDto.Data> fromEntity(
                Tracker tracker,
                Class<K> cls
            ) {
            return new TrackerDto.SingleTracker<>(TrackerDto.fromEntity(tracker, cls));
        }
    }

    private static Data fromEntity(Tracker tracker, Class<? extends Data> cls) {
        if (cls == Data.WithBook.class) {
            return new Data.WithBook(tracker, tracker.getBook());
        }

        return new Data.Compact(tracker, tracker.getBook());
    }
}
