package by.innowise.moviereview.service.impl;

import by.innowise.moviereview.dto.RateCreateDto;
import by.innowise.moviereview.dto.RatingDto;
import by.innowise.moviereview.dto.RatingUpdateRequest;
import by.innowise.moviereview.entity.Movie;
import by.innowise.moviereview.entity.Rating;
import by.innowise.moviereview.entity.User;
import by.innowise.moviereview.exception.NotFoundException;
import by.innowise.moviereview.exception.UserNotFoundException;
import by.innowise.moviereview.mapper.RateMapper;
import by.innowise.moviereview.repository.MovieRepository;
import by.innowise.moviereview.repository.RatingRepository;
import by.innowise.moviereview.repository.UserRepository;
import by.innowise.moviereview.service.interfaces.RatingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final RateMapper rateMapper;

    @Override
    @Transactional
    public RatingDto saveRating(RateCreateDto rateDto) {
        User user = userRepository.findById(rateDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Movie movie = movieRepository.findById(rateDto.getMovieId())
                .orElseThrow(() -> new NotFoundException("Фильм не найден"));
        Rating rating = new Rating()
                .setUser(user)
                .setMovie(movie)
                .setRating(rateDto.getRating())
                .setCreatedAt(LocalDateTime.now());
        Rating saved = ratingRepository.save(rating);

        log.info("User {} rating for movie {} added with tag {}", saved.getUser().getId(), saved.getMovie().getId(), saved.getRating());
        return rateMapper.toDto(saved);
    }

    @Override
    @Transactional
    public RatingDto updateRating(Long id, RatingUpdateRequest ratingUpdateRequest) {
        Rating optionalRating = ratingRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Рейтинг не найден"));
        optionalRating.setRating(ratingUpdateRequest.getRating());
        Rating rating = ratingRepository.save(optionalRating);
        log.info("User {} rating for movie {} updated with tag {}", rating.getUser().getId(), rating.getMovie().getId(), rating.getRating());
        return rateMapper.toDto(rating);
    }

    @Override
    public Integer getRatingByUserAndMovie(Long userId, Long movieId) {
        return ratingRepository.findByUserIdAndMovieId(userId, movieId)
                .map(Rating::getRating)
                .orElse(0);
    }

    @Override
    public Long getRatingIdByUserAndMovie(Long userId, Long movieId) {
        return ratingRepository.findByUserIdAndMovieId(userId, movieId)
                .map(Rating::getId)
                .orElse(0L);
    }

    @Override
    public Double getAverageRatingForMovie(Long movieId) {
        return ratingRepository.findAverageRatingByMovieId(movieId);
    }
}
