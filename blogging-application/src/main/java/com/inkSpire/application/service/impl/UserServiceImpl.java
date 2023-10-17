package com.inkSpire.application.service.impl;

import com.inkSpire.application.common.CommonServiceUtils;
import com.inkSpire.application.dto.user.UserRegistrationRequest;
import com.inkSpire.application.dto.user.UserRegistrationResponse;
import com.inkSpire.application.dto.user.UserUpdateResponse;
import com.inkSpire.application.entity.BlogPost;
import com.inkSpire.application.entity.Comment;
import com.inkSpire.application.entity.User;
import com.inkSpire.application.exception.AgeBelow18Exception;
import com.inkSpire.application.exception.UnauthorizedException;
import com.inkSpire.application.exception.UserAlreadyExistWithUsernameException;
import com.inkSpire.application.exception.UserNotFoundException;
import com.inkSpire.application.repository.UserRepository;
import com.inkSpire.application.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleService;
    private final JwtServiceImpl jwtService;
    private final CommonServiceUtils commonServiceUtils;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleServiceImpl roleService,
                           JwtServiceImpl jwtService,
                           CommonServiceUtils commonServiceUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtService = jwtService;
        this.commonServiceUtils = commonServiceUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(
                () -> {
                    LOGGER.error("Unable to fetch the user details for this email or username.");
                    return new UserNotFoundException("There is no user with this username or email.");
                }
        );
    }

    @Override
    public UserRegistrationResponse saveUser(UserRegistrationRequest userRegistrationRequest) {
        User user = extractUserFromRegistrationRequest("USER", userRegistrationRequest);
        userRepository.save(user);
        return new UserRegistrationResponse(
                user.getEmail(),
                user.getPassword(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getFirstname(),
                user.getLastname(),
                jwtService.generateToken(user.getUsername())
        );
    }

    @Override
    public UserRegistrationResponse saveAdmin(UserRegistrationRequest userRegistrationRequest) {
        User user = extractUserFromRegistrationRequest("ADMIN", userRegistrationRequest);
        userRepository.save(user);
        return new UserRegistrationResponse(
                user.getEmail(),
                user.getPassword(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getFirstname(),
                user.getLastname(),
                jwtService.generateToken(user.getUsername())
        );
    }

    @Override
    public UserUpdateResponse updateUser(String username, UserRegistrationRequest userRegistrationRequest) {
        final String loggedInUsername = commonServiceUtils.getLoggedInUsername();
        if (!loggedInUsername.equals(username)) {
            throw new UnauthorizedException("You are not authorized to delete this user's details.");
        }
        User existingUser = userRepository.findUserByEmail(username).orElseThrow(
                () -> {
                    LOGGER.error("Unable to fetch the user details. cause: Username not found.");
                    return new UserNotFoundException("There is no user associated with this username or email.");

                });

        assigningUserDetails(userRegistrationRequest, existingUser);

        userRepository.save(existingUser);
        return new UserUpdateResponse(
                existingUser.getEmail(),
                existingUser.getPassword(),
                existingUser.getGender(),
                existingUser.getDateOfBirth(),
                existingUser.getFirstname(),
                existingUser.getLastname(),
                existingUser.getAge()
        );
    }

    @Override
    public String deleteUser(String username) {
        final String loggedInUsername = commonServiceUtils.getLoggedInUsername();
        if (!loggedInUsername.equals(username)) {
            throw new UnauthorizedException("You are not authorized to delete this user's details.");
        }
        User existingUser = userRepository.findUserByEmail(username).orElseThrow(
                () -> {
                    LOGGER.error("Unable to fetch the user details. cause: There is no user with this email");
                    return new UserNotFoundException("There is no user with this email.");
                }
        );
        try {
            existingUser.getRoles().clear();
            existingUser.getComments().clear();
            existingUser.getBlogPosts().clear();
            userRepository.delete(existingUser);
            return "User successfully deleted.";
        } catch (Exception ex) {
            LOGGER.error("Unable to delete user. cause: {}", ex.getMessage());
            throw new RuntimeException("Unable to delete user " + ex.getMessage());
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(
                () -> {
                    LOGGER.error("Unable to fetch the user details. cause: There is no user with this email");
                    return new UserNotFoundException("There is no user with this email.");
                }
        );
    }

    private int[] calculateAgeFromDateOfBirth(Date birthDate) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(new Date());

        int years = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
        int months = currentCalendar.get(Calendar.MONTH) - birthCalendar.get(Calendar.MONTH);
        int days = currentCalendar.get(Calendar.DAY_OF_MONTH) - birthCalendar.get(Calendar.DAY_OF_MONTH);

        if (months < 0 || (months == 0 && days < 0)) {
            years--;
            if (months < 0) {
                months += 12;
            }
        }

        if (days < 0) {
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTime(birthDate);
            tempCalendar.add(Calendar.MONTH, 1);
            days += tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            months--;
        }

        return new int[]{years, months, days};
    }

    private User extractUserFromRegistrationRequest(String role, UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByEmail(userRegistrationRequest.getEmail())) {
            LOGGER.error("An error occurred while user registration.");
            throw new UserAlreadyExistWithUsernameException("There is a user associated with is email");
        }
        User user = new User();
        assigningUserDetails(userRegistrationRequest, user);
        List<Comment> comments = new ArrayList<>();
        List<BlogPost> posts = new ArrayList<>();

        user.setRoles(Set.of(roleService.getRoleByName(role.toUpperCase())));

        if (role.equalsIgnoreCase("Admin")) {
            user.setComments(null);
            user.setBlogPosts(null);
        } else {
            user.setBlogPosts(posts);
            user.setComments(comments);
        }
        return user;
    }

    private void assigningUserDetails(UserRegistrationRequest userRegistrationRequest, User user) {
        int age = calculateAgeFromDateOfBirth(userRegistrationRequest.getDateOfBirth())[0];
        if (age < 18) {
            LOGGER.warn("Unable to register user. cause: Age restrictions.");
            throw new AgeBelow18Exception("User must be at least 18 years old to register.");
        }
        user.setEmail(userRegistrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        user.setGender(userRegistrationRequest.getGender());
        user.setDateOfBirth(userRegistrationRequest.getDateOfBirth());
        user.setAge(age);
        user.setFirstname(userRegistrationRequest.getFirstname());
        user.setLastname(userRegistrationRequest.getLastname());
    }
}
