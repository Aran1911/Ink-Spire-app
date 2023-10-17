package com.inkSpire.application.dto.user;

import com.inkSpire.application.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getEmail(),
                user.getFirstname(),
                user.getLastname()
        );
    }
}
