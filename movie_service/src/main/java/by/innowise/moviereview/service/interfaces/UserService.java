package by.innowise.moviereview.service.interfaces;

import by.innowise.moviereview.dto.UserCreateDto;
import by.innowise.moviereview.dto.UserDto;

public interface UserService {
    UserDto findById(Long id);

    UserDto register(UserCreateDto userCreateDto);
}
