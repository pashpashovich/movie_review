package by.innowise.moviereview.service.interfaces;

import by.innowise.moviereview.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getAllUsers();

    void deleteUser(Long userId);

    void blockUser(Long userId);

    void unblockUser(Long userId);

    void promoteToAdmin(Long userId);

}
