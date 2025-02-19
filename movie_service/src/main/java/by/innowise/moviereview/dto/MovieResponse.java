package by.innowise.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class MovieResponse {
    private Long userId;
    private List<MovieDto> movies;
    private int totalPages;
    private List<EntityDto> genres;
    private List<MovieDto> recommendations;
}
