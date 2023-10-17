package com.inkSpire.application.controller;

import com.inkSpire.application.common.CommonServiceUtils;
import com.inkSpire.application.dto.authentication.AuthenticationRequest;
import com.inkSpire.application.dto.authentication.AuthenticationResponse;
import com.inkSpire.application.dto.user.UserRegistrationRequest;
import com.inkSpire.application.dto.user.UserRegistrationResponse;
import com.inkSpire.application.entity.Gender;
import com.inkSpire.application.entity.Role;
import com.inkSpire.application.service.impl.JwtServiceImpl;
import com.inkSpire.application.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest extends AbstractTest {

    private static final String baseUrl = "/users";

    @Mock
    private UserServiceImpl service;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private CommonServiceUtils commonServiceUtils;

    @Mock
    private AuthenticationManager manager;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        super.setUp();
    }

    @Order(1)
    @Test
    void registerUser() throws Exception {

        String input = mapToJson(new UserRegistrationRequest(
                "john@gmail.com",
                "user_pass",
                Gender.MALE,  // Use the correct enum value
                toDate("1991-02-14"),
                "John",
                "Doe"
        ));

        Role userRole = new Role("USER", "User of this application.");
        MvcResult result = mockMvc.perform(
                post(baseUrl + "/u-register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();
        UserRegistrationResponse user = mapFromJson(result.getResponse().getContentAsString(), UserRegistrationResponse.class);
        assertEquals(201, result.getResponse().getStatus());
        assertNotNull(user);
        assertEquals("John", user.getFirstname());
        assertNotNull(user.getJwtToken());

    }

    @Order(2)
    @Test
    void registerUser_fail1() throws Exception {

        String input = mapToJson(new UserRegistrationRequest(
                        "",
                        "user_pass",
                        Gender.MALE,
                        toDate("1991-02-14"),
                        "John",
                        "Doe"
                )
        );

        MvcResult result = mockMvc.perform(
                post(baseUrl + "/u-register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();
        HashMap errors = mapFromJson(result.getResponse().getContentAsString(), HashMap.class);
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Email is required.", errors.get("email"));

    }

    @Order(3)
    @Test
    void registerUser_fail2() throws Exception {

        String input = mapToJson(new UserRegistrationRequest(
                        "john@gmail.com",
                        "",
                        Gender.MALE,
                        toDate("1991-02-14"),
                        "John",
                        "Doe"
                )
        );

        MvcResult result = mockMvc.perform(
                post(baseUrl + "/u-register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();
        HashMap errors = mapFromJson(result.getResponse().getContentAsString(), HashMap.class);
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Password is required.", errors.get("password"));

    }

    @Order(4)
    @Test
    void registerUser_fail3() throws Exception {

        String input = mapToJson(new UserRegistrationRequest(
                        "john@gmail.com",
                        "user_pass",
                        Gender.MALE,
                        null,
                        "John",
                        "Doe"
                )
        );

        MvcResult result = mockMvc.perform(
                post(baseUrl + "/u-register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();
        HashMap errors = mapFromJson(result.getResponse().getContentAsString(), HashMap.class);
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Date of birth is required.", errors.get("dateOfBirth"));

    }

    @Order(5)
    @Test
    void registerUser_fail4() throws Exception {
        String input = mapToJson(new UserRegistrationRequest(
                        "john@gmail.com",
                        "user_pass",
                        Gender.MALE,
                        toDate("1991-02-14"),
                        "",
                        "Doe"
                )
        );

        MvcResult result = mockMvc.perform(
                post(baseUrl + "/u-register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();
        HashMap errors = mapFromJson(result.getResponse().getContentAsString(), HashMap.class);
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Firstname is required.", errors.get("firstname"));

    }

    @Order(6)
    @Test
    void registerUser_fail5() throws Exception {
        String input = mapToJson(new UserRegistrationRequest(
                        "john@gmail.com",
                        "user_pass",
                        Gender.MALE,
                        toDate("1991-02-14"),
                        "John",
                        ""
                )
        );

        MvcResult result = mockMvc.perform(
                post(baseUrl + "/u-register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();
        HashMap errors = mapFromJson(result.getResponse().getContentAsString(), HashMap.class);
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Lastname is required.", errors.get("lastname"));

    }

    @Order(7)
    @Test
    void generateAuthenticationResponse() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user@example.com", "user_pass");
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Mock the behavior of manager.authenticate()
        when(manager.authenticate(authentication)).thenReturn(authentication);

        // Mock the behavior of jwtService
        when(jwtService.generateAuthenticationResponse(authenticationRequest)).thenReturn(new AuthenticationResponse("token"));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.data.token").value("token"));

    }


    @Test
    void registerAdmin() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    private Date toDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Cause: " + e.getLocalizedMessage());
        }
    }
}