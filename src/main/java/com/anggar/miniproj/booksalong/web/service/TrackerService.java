package com.anggar.miniproj.booksalong.web.service;

import com.anggar.miniproj.booksalong.data.entity.Book;
import com.anggar.miniproj.booksalong.data.entity.Tracker;
import com.anggar.miniproj.booksalong.data.entity.User;
import com.anggar.miniproj.booksalong.data.entity.enums.TrackingStateEnum;
import com.anggar.miniproj.booksalong.data.entity.idclass.TrackerId;
import com.anggar.miniproj.booksalong.data.repository.TrackerRepository;
import com.anggar.miniproj.booksalong.web.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackerService {

    @Autowired
    private TrackerRepository trackerRepository;

    public Tracker findOne(long userId, long bookId) {
        var id = new TrackerId(userId, bookId);

        return trackerRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Tracker.class));
    }

    public Tracker upsertWishlist(Book book, User user) {
        var tracker = trackerRepository.findById(new TrackerId(user.getId(), book.getId()))
                .map(savedTracker -> {
                    switch (savedTracker.getState()) {
                        case UNTRACKED -> savedTracker.setState(TrackingStateEnum.WISHLISTED);
                        case WISHLISTED -> savedTracker.setState(TrackingStateEnum.UNTRACKED);
                        default -> throw new IllegalStateException("Unexpected actionState transition to wishlist: " + savedTracker.getState());
                    }

                    savedTracker.setCurrentPage(0);
                    return savedTracker;
                })
                .orElse(new Tracker(0, TrackingStateEnum.WISHLISTED, user, book));

        return trackerRepository.save(tracker);
    }

    public Tracker trackPage(Book book, User user, long currentPage) {
        var tracker = trackerRepository.findById(new TrackerId(user.getId(), book.getId()))
                .map(savedTracker -> {
                    savedTracker.setState(TrackingStateEnum.TRACKED);
                    savedTracker.setCurrentPage(currentPage);
                    return savedTracker;
                })
                .orElse(new Tracker(currentPage, TrackingStateEnum.TRACKED, user, book));

        return trackerRepository.save(tracker);
    }

    public Tracker finishBook(Book book, User user) {
        var tracker = trackerRepository.findById(new TrackerId(user.getId(), book.getId()))
                .map(savedTracker -> {
                    switch (savedTracker.getState()) {
                        case TRACKED, FINISHED -> {
                            savedTracker.setCurrentPage(book.getPageNumbers());
                            savedTracker.setState(TrackingStateEnum.FINISHED);
                        }
                        default -> throw new IllegalStateException("Unexpected actionState transition to finish: " + savedTracker.getState());
                    }
                    return savedTracker;
                })
                .orElse(new Tracker(book.getPageNumbers(), TrackingStateEnum.FINISHED, user, book));

        return trackerRepository.save(tracker);
    }

    public Tracker untrack(Book book, User user) {
        var tracker = trackerRepository.findById(new TrackerId(user.getId(), book.getId()))
                .map(savedTracker -> {
                    savedTracker.setState(TrackingStateEnum.UNTRACKED);
                    savedTracker.setCurrentPage(0);
                    return savedTracker;
                })
                .orElse(new Tracker(0, TrackingStateEnum.UNTRACKED, user, book));

        return trackerRepository.save(tracker);
    }
}
