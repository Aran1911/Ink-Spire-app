package com.inkSpire.application.controller;

import com.inkSpire.application.common.ApiResponse;
import com.inkSpire.application.common.CommonServiceUtils;
import com.inkSpire.application.dto.authentication.AuthenticationRequest;
import com.inkSpire.application.dto.authentication.AuthenticationResponse;
import com.inkSpire.application.dto.user.UserRegistrationRequest;
import com.inkSpire.application.dto.user.UserRegistrationResponse;
import com.inkSpire.application.dto.user.UserUpdateResponse;
import com.inkSpire.application.service.impl.JwtServiceImpl;
import com.inkSpire.application.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:4200/sign-up")
public class UserController {
    private final UserServiceImpl service;
    private final JwtServiceImpl jwtService;
    private final CommonServiceUtils commonServiceUtils;
    private final AuthenticationManager manager;

    public UserController(UserServiceImpl service, JwtServiceImpl jwtService, CommonServiceUtils commonServiceUtils, AuthenticationManager manager) {
        this.service = service;
        this.jwtService = jwtService;
        this.commonServiceUtils = commonServiceUtils;
        this.manager = manager;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> generateAuthenticationResponse(@RequestBody @Valid @NotNull AuthenticationRequest request) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            true,
                            jwtService.generateAuthenticationResponse(request)
                    ), HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(commonServiceUtils.generateResponse(
                    false,
                    "Unauthorized"
            ), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/u-register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@NotNull @Valid @RequestBody UserRegistrationRequest user) {
        return new ResponseEntity<>(service.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/a-register")
    public ResponseEntity<UserRegistrationResponse> registerAdmin(@NotNull @Valid @RequestBody UserRegistrationRequest admin) {
        return new ResponseEntity<>(service.saveAdmin(admin), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserUpdateResponse>> updateUser(
            @RequestParam(name = "un") String un,
            @RequestBody @NotNull @Valid UserRegistrationRequest userUpdateRequest) {
        if (commonServiceUtils.isUserAuthenticated()) {
            final String loggedInUsername = commonServiceUtils.getLoggedInUsername();
            if (loggedInUsername.equals(un)) {
                UserUpdateResponse updatedUser = service.updateUser(un, userUpdateRequest);
                return new ResponseEntity<>(commonServiceUtils.generateResponse(
                        true,
                        "User details updated successfully.",
                        updatedUser),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(commonServiceUtils.generateResponse(
                        false,
                        "You are not authorized to update this user's details."
                ),
                        HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(commonServiceUtils.generateResponse(
                    false,
                    "Authentication required to update user details."
            ),
                    HttpStatus.UNAUTHORIZED);
        }
    }


    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteUser(@RequestParam(name = "un") String un) {
        if (commonServiceUtils.isUserAuthenticated()) {
            final String loggedInUsername = commonServiceUtils.getLoggedInUsername();
            if (loggedInUsername.equals(un)) {
                String response = service.deleteUser(un);
                return new ResponseEntity<>(commonServiceUtils.generateResponse(
                        true,
                        "User details deleted successfully.",
                        response),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(commonServiceUtils.generateResponse(
                        false,
                        "You are not authorized to delete this user's details."
                ),
                        HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(commonServiceUtils.generateResponse(
                    false,
                    "Authentication required to delete user details."
            ),
                    HttpStatus.UNAUTHORIZED);
        }
    }

}
