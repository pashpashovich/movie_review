package by.innowise.moviereview.controller;

import by.innowise.moviereview.dto.WatchlistDto;
import by.innowise.moviereview.dto.WatchlistRequest;
import by.innowise.moviereview.service.interfaces.WatchlistService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<WatchlistDto>> getWatchlist(@PathVariable("userId") @NotNull Long userId) {
        List<WatchlistDto> watchlist = watchlistService.getWatchlistByUserId(userId);
        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkMovieInWatchlist(
            @RequestParam Long userId,
            @RequestParam Long movieId) {
        boolean movieInWatchlist = watchlistService.isMovieInWatchlist(userId, movieId);
        return ResponseEntity.ok(movieInWatchlist);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToWatchlist(@RequestBody @Valid WatchlistRequest request) {
        if (watchlistService.isMovieInWatchlist(request.getUserId(), request.getMovieId())) {
            return ResponseEntity.badRequest().body("Фильм уже в избранном");
        }
        watchlistService.addToWatchlist(request.getUserId(), request.getMovieId());
        return ResponseEntity.ok("Фильм добавлен в избранное");
    }

    @DeleteMapping()
    public ResponseEntity<Void> removeFromWatchlist(@RequestParam("movieId") @NotNull Long movieId, @RequestParam("userId") @NotNull Long userId) {
        watchlistService.removeFromWatchlist(userId, movieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
