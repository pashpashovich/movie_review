package by.innowise.moviereview.facade;

import by.innowise.moviereview.dto.MovieDetailsDto;
import by.innowise.moviereview.exception.EntityNotFoundException;
import by.innowise.moviereview.service.interfaces.MovieService;
import by.innowise.moviereview.service.interfaces.RatingService;
import by.innowise.moviereview.service.interfaces.ReviewService;
import by.innowise.moviereview.service.interfaces.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieDetailsFacade {

    private final RatingService ratingService;
    private final MovieService movieService;
    private final ReviewService reviewService;
    private final WatchlistService watchlistService;

    public MovieDetailsDto getMovieDetails(Long movieId, Long userId) throws EntityNotFoundException {
        return MovieDetailsDto.builder()
                .userId(userId)
                .movie(movieService.getMovieById(movieId))
                .averageRating(ratingService.getAverageRatingForMovie(movieId))
                .reviews(reviewService.findApprovedReviewsByMovieId(movieId))
                .isInList(watchlistService.isMovieInWatchlist(userId, movieId))
                .userRating(ratingService.getRatingByUserAndMovie(userId, movieId))
                .ratingId(ratingService.getRatingIdByUserAndMovie(userId, movieId))
                .build();
    }
}
