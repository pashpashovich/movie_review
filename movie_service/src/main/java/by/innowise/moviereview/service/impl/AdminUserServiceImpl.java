package by.innowise.moviereview.service.impl;

import by.innowise.moviereview.dto.UserDto;
import by.innowise.moviereview.entity.User;
import by.innowise.moviereview.enums.Role;
import by.innowise.moviereview.exception.NotFoundException;
import by.innowise.moviereview.mapper.UserMapper;
import by.innowise.moviereview.repository.UserRepository;
import by.innowise.moviereview.service.interfaces.AdminUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Пользователь с ID %s не найден";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toListDto(users).stream()
                .filter(userDto -> userDto.getRole().equals(Role.USER))
                .toList();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, userId)));
        userRepository.delete(user);
        log.info("User with id {} deleted", userId);
    }

    @Override
    public void blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, userId)));
        user.setIsBlocked(true);
        userRepository.save(user);
        log.info("User with id {} blocked", userId);
    }

    @Override
    public void unblockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, userId)));
        user.setIsBlocked(false);
        userRepository.save(user);
        log.info("User with id {} is unblocked", userId);
    }

    @Override
    public void promoteToAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, userId)));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        log.info("User with id {} has been promoted to administrator role", userId);
    }
}

