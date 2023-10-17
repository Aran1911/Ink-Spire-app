package com.inkSpire.application.service;

import com.inkSpire.application.dto.user.UserRegistrationRequest;
import com.inkSpire.application.dto.user.UserRegistrationResponse;
import com.inkSpire.application.dto.user.UserUpdateResponse;
import com.inkSpire.application.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserRegistrationResponse saveUser(UserRegistrationRequest userRegistrationRequest);

    UserRegistrationResponse saveAdmin(UserRegistrationRequest userRegistrationRequest);

    UserUpdateResponse updateUser(String username, UserRegistrationRequest userRegistrationRequest);

    String deleteUser(String username);

    User getUserByUsername(String username);


}