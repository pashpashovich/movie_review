package by.innowise.moviereview.service.interfaces;

import by.innowise.moviereview.dto.RateCreateDto;
import by.innowise.moviereview.dto.RatingDto;
import by.innowise.moviereview.dto.RatingUpdateRequest;

public interface RatingService {
    RatingDto saveRating(RateCreateDto rateDto);
    RatingDto updateRating(Long id, RatingUpdateRequest ratingUpdateRequest);
    Integer getRatingByUserAndMovie(Long userId, Long movieId);
    Long getRatingIdByUserAndMovie(Long userId, Long movieId);
    Double getAverageRatingForMovie(Long movieId);
}
