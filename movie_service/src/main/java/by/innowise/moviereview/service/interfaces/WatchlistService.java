package by.innowise.moviereview.service.interfaces;

import by.innowise.moviereview.dto.WatchlistDto;

import java.util.List;

public interface WatchlistService {
    void addToWatchlist(Long userId, Long movieId);
    List<WatchlistDto> getWatchlistByUserId(Long userId);
    void removeFromWatchlist(Long userId, Long movieId);
    boolean isMovieInWatchlist(Long userId, Long movieId);
}
