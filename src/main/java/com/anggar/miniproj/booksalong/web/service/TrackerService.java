package com.anggar.miniproj.booksalong.web.service;

import com.anggar.miniproj.booksalong.data.entity.Book;
import com.anggar.miniproj.booksalong.data.entity.Tracker;
import com.anggar.miniproj.booksalong.data.entity.User;
import com.anggar.miniproj.booksalong.data.entity.enums.TrackingStateEnum;
import com.anggar.miniproj.booksalong.data.entity.idclass.TrackerId;
import com.anggar.miniproj.booksalong.data.repository.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackerService {

    @Autowired
    private TrackerRepository trackerRepository;

    public Tracker upsertWishlist(Book book, User user) {
        var trackerObj = Tracker.builder()
                .book(book)
                .user(user)
                .currentPage(0)
                .build();

        trackerRepository.findById(new TrackerId(user.getId(), book.getId()))
                .ifPresentOrElse(tracker -> {
                    trackerObj.setCreatedAt(tracker.getCreatedAt());

                    switch (tracker.getState()) {
                        case UNTRACKED -> trackerObj.setState(TrackingStateEnum.WISHLISTED);
                        case WISHLISTED -> trackerObj.setState(TrackingStateEnum.UNTRACKED);
                        default -> throw new IllegalStateException("Unexpected actionState transition to wishlist: " + tracker.getState());
                    };
                }, () -> {
                    trackerObj.setState(TrackingStateEnum.WISHLISTED);
                });

        return trackerRepository.save(trackerObj);
    }

    public Tracker trackPage(Book book, User user, long currentPage) {
        var trackerObj = Tracker.builder()
                .book(book)
                .user(user)
                .currentPage(currentPage)
                .state(TrackingStateEnum.TRACKED)
                .build();

        return trackerRepository.save(trackerObj);
    }

    public Tracker finishBook(Book book, User user) {
        var trackerObj = Tracker.builder()
                .book(book)
                .user(user)
                .currentPage(book.getPageNumbers())
                .state(TrackingStateEnum.FINISHED)
                .build();

        trackerRepository.findById(new TrackerId(user.getId(), book.getId()))
                .ifPresentOrElse(tracker -> {
                    trackerObj.setCreatedAt(tracker.getCreatedAt());

                    switch (tracker.getState()) {
                        case TRACKED, FINISHED -> trackerObj.setState(TrackingStateEnum.FINISHED);
                        default -> throw new IllegalStateException("Unexpected actionState transition to finish: " + tracker.getState());
                    };
                }, () -> {
                    throw new IllegalStateException("Unexpected actionState transition to finish.");
                });

        return trackerRepository.save(trackerObj);
    }

    public Tracker untrack(Book book, User user) {
        var trackerObj = Tracker.builder()
                .book(book)
                .user(user)
                .currentPage(0)
                .state(TrackingStateEnum.UNTRACKED)
                .build();

        return trackerRepository.save(trackerObj);
    }

}
