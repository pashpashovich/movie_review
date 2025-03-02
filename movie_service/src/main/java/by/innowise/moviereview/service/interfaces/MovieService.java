package by.innowise.moviereview.service.interfaces;

import by.innowise.moviereview.dto.MovieCreateDto;
import by.innowise.moviereview.dto.MovieDto;
import by.innowise.moviereview.dto.MovieFilterRequest;
import by.innowise.moviereview.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {
    Page<MovieDto> getMoviesWithPagination(int page, int pageSize);

    MovieDto getMovieById(Long id) throws EntityNotFoundException;

    MovieDto createMovie(MovieCreateDto movieDto);

    MovieDto updateMovie(Long id, MovieCreateDto movieDto) throws EntityNotFoundException;

    void deleteMovie(Long id);

    void updateMoviePoster(Long id, MultipartFile posterFile) throws EntityNotFoundException;

    Page<MovieDto> filterMoviesWithPagination(String searchQuery, int page, int size);

    Page<MovieDto> filterMoviesWithPagination(MovieFilterRequest movieFilterRequest);
}
