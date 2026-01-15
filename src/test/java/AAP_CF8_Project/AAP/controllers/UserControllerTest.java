package AAP_CF8_Project.AAP.controllers;

import AAP_CF8_Project.AAP.controller.UserController;
import AAP_CF8_Project.AAP.security.Role;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {
/*
MockMvc is a Spring testing utility that allows you to simulate HTTP requests to your controllers without starting a real web server.
It runs entirely in memory, so it’s fast.
It goes through Spring MVC’s DispatcherServlet, so your controller’s request mapping, model binding, validation, and view resolution are all tested.
You can mock service beans to isolate the controller logic, or run against real services for full integration tests.

Think of it as a fake browser inside your unit/integration test.
 */
    private MockMvc mockMvc;
    private UserService userService; // plain Mockito mock
    private PasswordEncoder passwordEncoder;

    private User existingUser;

    @BeforeEach
    void setup() {
        // Create mock service
        userService = Mockito.mock(UserService.class);

        // Initialize controller manually with the mock
        UserController controller = new UserController(userService);

        // Setup MockMvc standalone
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        passwordEncoder = new BCryptPasswordEncoder();

        // Example existing user
        existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("john");
        existingUser.setEmail("john@example.com");
        existingUser.setPasswordHash(passwordEncoder.encode("secret"));
        existingUser.setRole(Role.USER);
        existingUser.setCreatedDate(LocalDateTime.now());
        existingUser.setLastLogin(LocalDateTime.now());
    }

    // ------------------- GET /users -------------------
//    @Test
//    void getUsers_shouldReturnUserListView() throws Exception {
//        when(userService.findAll()).thenReturn(List.of(existingUser));
//
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("users"));
//
//        verify(userService, times(2)).findAll(); // called twice in controller
//    }

    // ------------------- GET /users/new -------------------
    @Test
    void getNewUserForm_shouldReturnUserFormView() throws Exception {
        mockMvc.perform(get("/users/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(model().attributeExists("newuser"));
    }

    // ------------------- POST /users -------------------
    @Test
    void postSaveUser_shouldCreateNewUserAndRedirect() throws Exception {
        when(userService.existsById(anyInt())).thenReturn(false);

        // Simulate save setting ID
        doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(2);
            return null;
        }).when(userService).save(any(User.class));

        mockMvc.perform(post("/users")
                        .param("username", "alice")
                        .param("email", "alice@example.com")
                        .param("passwordHash", "password")
                        .contentType("application/x-www-form-urlencoded"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/profile/*"));

        verify(userService, times(1)).save(any(User.class));
    }

    // ------------------- GET /users/edit/{id} -------------------
    @Test
    void getEditUserForm_shouldReturnUserFormForExistingUser() throws Exception {
        when(userService.findById(1)).thenReturn(existingUser);

        mockMvc.perform(get("/users/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findById(1);
    }

    @Test
    void getEditUserForm_shouldRedirectWhenUserNotFound() throws Exception {
        when(userService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/users/edit/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService, times(1)).findById(99);
    }

    // ------------------- GET /users/delete/{id} -------------------
    @Test
    void deleteUser_shouldCallServiceAndRedirect() throws Exception {
        doNothing().when(userService).deleteById(1);

        mockMvc.perform(get("/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).deleteById(1);
    }
}
