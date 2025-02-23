package by.innowise.moviereview.controller;

import by.innowise.moviereview.dto.ErrorResponseImpl;
import by.innowise.moviereview.dto.MovieCreateDto;
import by.innowise.moviereview.dto.MovieDto;
import by.innowise.moviereview.exception.EntityNotFoundException;
import by.innowise.moviereview.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseImpl> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseImpl(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now()));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getMovies(
            @RequestParam(value = "page", defaultValue = "1") @NotNull int page,
            @RequestParam(value = "pageSize", defaultValue = "5") @NotNull int pageSize,
            @RequestParam(value = "title", required = false) @NotNull String query) {
        List<MovieDto> movies = (query == null)
                ? movieService.getMoviesWithPagination(page, pageSize)
                : movieService.filterMoviesWithPagination(query, page, pageSize);
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieCreateDto movieDto) {
        MovieDto createdMovie = movieService.createMovie(movieDto);
        return ResponseEntity.ok(createdMovie);
    }

    @PostMapping(value = "/{id}/poster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadMoviePoster(
            @PathVariable @NotNull Long id,
            @RequestPart("posterFile") @NotNull MultipartFile posterFile) throws EntityNotFoundException, IOException {
        movieService.updateMoviePoster(id, posterFile);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MovieDto> updateMovie(
            @PathVariable("id") @NotNull Long id,
            @RequestBody @Valid MovieCreateDto movieDto) throws EntityNotFoundException {
        MovieDto updatedMovie = movieService.updateMovie(id, movieDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable @NotNull Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
