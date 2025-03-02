package by.innowise.moviereview.facade;

import by.innowise.moviereview.dto.EntityDto;
import by.innowise.moviereview.dto.MovieDto;
import by.innowise.moviereview.dto.MovieFilterRequest;
import by.innowise.moviereview.dto.MovieResponse;
import by.innowise.moviereview.service.interfaces.GenreService;
import by.innowise.moviereview.service.interfaces.MovieService;
import by.innowise.moviereview.service.interfaces.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMovieFacade {
    private final MovieService movieService;
    private final GenreService genreService;
    private final RecommendationService recommendationService;

    public MovieResponse getResponse(Long userId, MovieFilterRequest filterRequest) {
        Page<MovieDto> moviesPage = movieService.filterMoviesWithPagination(filterRequest);

        List<EntityDto> genres = genreService.findAll();
        List<MovieDto> recommendations = recommendationService.getRecommendationsForUser(userId);

        return MovieResponse.builder()
                .userId(userId)
                .movies(moviesPage.getContent())
                .totalPages(moviesPage.getTotalPages())
                .genres(genres)
                .recommendations(recommendations)
                .build();
    }
}
