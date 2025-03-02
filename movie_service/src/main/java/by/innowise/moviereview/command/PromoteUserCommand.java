package by.innowise.moviereview.command;

import by.innowise.moviereview.service.impl.AdminUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromoteUserCommand implements UserCommand {

    private final AdminUserServiceImpl adminUserService;

    @Override
    public void execute(Long userId) {
        adminUserService.promoteToAdmin(userId);
    }
}

