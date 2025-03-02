package by.innowise.moviereview.service.interfaces;

import by.innowise.moviereview.dto.MovieDto;

import java.util.List;

public interface RecommendationService {
    List<MovieDto> getRecommendationsForUser(Long userId);
}
