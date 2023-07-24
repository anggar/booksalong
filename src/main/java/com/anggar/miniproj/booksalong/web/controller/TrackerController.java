package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.data.dto.TrackerDto;
import com.anggar.miniproj.booksalong.security.AuthUserDetails;
import com.anggar.miniproj.booksalong.web.service.BookService;
import com.anggar.miniproj.booksalong.web.service.TrackerService;
import com.anggar.miniproj.booksalong.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracker")
public class TrackerController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TrackerService trackerService;

    @GetMapping("/")
    public TrackerDto.SingleTracker<? extends TrackerDto.Data> findByBookId(
            @AuthenticationPrincipal AuthUserDetails authUserDetails,
            @RequestParam long bookId
    ) {
        var user = userService.currentUser(authUserDetails);
        var book = bookService.findById(bookId);
        var tracker = trackerService.findOne(user.getId(), book.getId());

        return TrackerDto.SingleTracker.fromEntity(tracker, TrackerDto.Data.WithBook.class);
    }

    @PostMapping("/")
    public TrackerDto.SingleTracker<? extends TrackerDto.Data> create(
            @AuthenticationPrincipal AuthUserDetails authUserDetails,
            @RequestBody @Valid TrackerDto.TrackRequest body
    ) {
        var user = userService.currentUser(authUserDetails);
        var book = bookService.findById(body.bookId());


        var tracker = switch (body.actionState()) {
            case "wishlist" -> trackerService.upsertWishlist(book, user);
            case "trackPage" -> trackerService.trackPage(book, user, body.currentPage());
            case "finish" -> trackerService.finishBook(book, user);
            case "untrack" -> trackerService.untrack(book, user);
            default -> throw new IllegalStateException("Unexpected action state: " + body.actionState());
        };

        return TrackerDto.SingleTracker.fromEntity(tracker, TrackerDto.Data.WithBook.class);
    }
}
