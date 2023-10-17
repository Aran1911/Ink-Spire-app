package com.inkSpire.application.controller;

import com.inkSpire.application.entity.Role;
import com.inkSpire.application.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleControllerTest extends AbstractTest {

    @Mock
    private RoleServiceImpl roleService;

    @InjectMocks
    private RoleController controller;

    private static final String baseUrl = "/roles";


    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }


    @Order(1)
    @Test
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    void createNewRole() throws Exception {
        Role role = new Role("Guest", "Guest user.");

        String input = super.mapToJson(role);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(201, result.getResponse().getStatus());
        assertEquals(role.getRoleName().toUpperCase(), mapFromJson(result.getResponse().getContentAsString(), Role.class).getRoleName());
        assertEquals(role.getRoleDescription(), mapFromJson(result.getResponse().getContentAsString(), Role.class).getRoleDescription());
    }

    @Order(2)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void createNewRole_fail1() throws Exception {
        Role role = new Role(3L, "GUEST", "Guest user of this application");
        String input = super.mapToJson(role);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(400, result.getResponse().getStatus());
        assertEquals("There is a role already exist with name.", result.getResponse().getContentAsString());
    }

    @Order(3)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void createNewRole_fail2() throws Exception {
        Role role = new Role(3L, "", "Guest role of this application.");
        String input = super.mapToJson(role);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Role name is required.", result.getResponse().getContentAsString());

    }

    @Order(4)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void createNewRole_fail3() throws Exception {
        Role role = new Role("SUPER_ADMIN", "");
        String input = super.mapToJson(role);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Role description is required.", result.getResponse().getContentAsString());

    }


    @Order(5)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void updateRoleDetails() throws Exception {
        Role role = new Role("Guest", "Guest user of this application.");

        String input = mapToJson(role);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "?roleName=guest").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals(role.getRoleName().toUpperCase(), mapFromJson(result.getResponse().getContentAsString(), Role.class).getRoleName());
        assertEquals(role.getRoleDescription(), mapFromJson(result.getResponse().getContentAsString(), Role.class).getRoleDescription());
    }

    @Order(6)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void updateRoleDetails_fail1() throws Exception {
        Role role = new Role("Super_admin", "Super admin user of this application.");

        String input = mapToJson(role);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "?roleName=Super_admin").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(404, result.getResponse().getStatus());
        assertEquals("There is no role with this role name.", result.getResponse().getContentAsString());
    }

    @Order(7)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void updateRoleDetails_fail2() throws Exception {
        String input = mapToJson(null);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "?roleName=admin").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(400, result.getResponse().getStatus());

    }

    @Order(8)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void updateRoleDetails_fail3() throws Exception {
        Role role = new Role("admin", "");
        String input = super.mapToJson(role);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "?roleName=admin").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Role description is required.", result.getResponse().getContentAsString());
    }

    @Order(9)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void updateRoleDetails_fail4() throws Exception {
        Role role = new Role("ADMIN", "Admin user of this application.");
        String input = super.mapToJson(role);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "?roleName=user").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Duplicate entry. Please provide a unique value.", result.getResponse().getContentAsString());

    }

    @Order(10)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void updateRoleDetails_fail5() throws Exception {
        Role role = new Role("user", "");
        String input = super.mapToJson(role);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "?roleName=user").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(input)
        ).andReturn();

        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Role description is required.", result.getResponse().getContentAsString());

    }

    // This method in the controller layer need to be removed.
    @Order(11)
    @WithMockUser(username = "user.test@gmail.com", password = "admin_pass", authorities = {"ROLE_ADMIN"})
    @Test
    void getAllRoles() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/all").accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals(3,mapFromJson(result.getResponse().getContentAsString(), Role[].class).length);
    }

    @Order(12)
    @Test
    void initRoles() {
        controller.initRoles();
        verify(roleService).init();
    }
}