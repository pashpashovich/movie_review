package by.innowise.moviereview.controller;

import by.innowise.moviereview.command.UserCommandFactory;
import by.innowise.moviereview.dto.UserDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserCommandFactory userCommandFactory;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userCommandFactory.getAllUsers());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Map<String, String>> handleUserAction(@PathVariable @NotNull Long userId,
                                                                @RequestParam @NotNull String action) {
        var command = userCommandFactory.getCommand(action);
        if (command == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Unknown action: " + action));
        }
        command.execute(userId);
        return ResponseEntity.ok(Map.of("message", "User " + action + " successfully"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> handleUserAction(@PathVariable @NotNull Long userId) {
        userCommandFactory.getCommand("delete").execute(userId);
        return ResponseEntity.ok(Map.of("message", String.format("User with id %s deleted successfully", userId)));
    }

}
