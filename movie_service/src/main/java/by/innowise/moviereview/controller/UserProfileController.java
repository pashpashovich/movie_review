package by.innowise.moviereview.controller;

import by.innowise.moviereview.dto.ErrorResponseImpl;
import by.innowise.moviereview.dto.ReviewDto;
import by.innowise.moviereview.dto.UserDto;
import by.innowise.moviereview.exception.UserNotFoundException;
import by.innowise.moviereview.service.interfaces.ReviewService;
import by.innowise.moviereview.service.interfaces.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("api/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;
    private final ReviewService reviewService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseImpl> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseImpl(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> viewProfile(@PathVariable("id") @NotNull Long id) {
        UserDto userDetails = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> viewReviews(@PathVariable("id") @NotNull Long id) {
        List<ReviewDto> reviewList = reviewService.findRecentReviewsByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(reviewList);
    }
}
