package by.innowise.moviereview.utils;

import by.innowise.moviereview.dto.UserDto;
import by.innowise.moviereview.entity.Genre;
import by.innowise.moviereview.entity.Movie;
import by.innowise.moviereview.entity.Person;
import by.innowise.moviereview.entity.User;
import by.innowise.moviereview.enums.MovieRole;
import by.innowise.moviereview.enums.Role;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;

@UtilityClass
public class TestBuilder {

    public static Genre createGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        return genre;
    }

    public static Movie createMovie(String title, Set<Genre> genres) {
        return new Movie()
                .setTitle(title)
                .setGenres(genres)
                .setLanguage("Русский")
                .setReleaseYear(2023)
                .setDuration(134);
    }

    public static User createUser(String name, String email) {
        return new User()
                .setUsername(name)
                .setEmail(email)
                .setPassword("1234567gfdsdfvb")
                .setRole(Role.USER);
    }

    public static Person createPerson(String fullName, MovieRole movieRole) {
        Person person = new Person();
        person.setFullName(fullName);
        person.setRole(movieRole);
        person.setMovies(List.of(createMovie("Movie1", Set.of(createGenre("Драма")))));
        return person;
    }

    public static UserDto createUserDto(Long id, String email, Role userRole, String username) {
        return UserDto.builder()
                .id(id)
                .email(email)
                .role(userRole)
                .username(username)
                .build();

    }
}