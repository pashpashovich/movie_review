package by.innowise.moviereview.service.interfaces;

import by.innowise.moviereview.dto.ReviewDto;
import by.innowise.moviereview.dto.ReviewRequest;

import java.util.List;

public interface ReviewService {
    ReviewDto addReview(ReviewRequest reviewRequest);
    List<ReviewDto> findAllPendingReviews();
    ReviewDto updateReviewStatus(Long reviewId, String status);
    List<ReviewDto> findApprovedReviewsByMovieId(Long movieId);
    List<ReviewDto> findRecentReviewsByUserId(Long userId);
}
