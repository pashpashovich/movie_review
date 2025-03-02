package by.innowise.moviereview.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MovieFilterRequest {
    @NotNull
    @Min(0)
    private int page;
    @NotNull
    @Min(1)
    private int size;
    private String searchQuery;
    private String genreId;
    private String language;
    @Min(1890)
    @Max(2030)
    private String year;
    @Min(10)
    @Max(600)
    private String duration;
}
