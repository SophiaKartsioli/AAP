package AAP_CF8_Project.AAP.controllers;


import AAP_CF8_Project.AAP.controller.LoginController;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginControllerTest {

    private MockMvc mockMvc;
    private LoginService loginService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        loginService = Mockito.mock(LoginService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);

        LoginController controller = new LoginController(loginService, passwordEncoder);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // ------------------- GET /login -------------------
    @Test
    void showLogin_shouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login_page"))
                .andExpect(model().attributeExists("loginUser"));
    }

    // ------------------- POST /login (Success) -------------------
    @Test
    void login_successfulAuthentication_shouldRedirectToProfile() throws Exception {
        // Arrange
        User mockUser = new User();
        mockUser.setId(101);
        mockUser.setPasswordHash("encoded_password");

        when(loginService.findByUsernameOrEmail("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("raw_password", "encoded_password")).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/login")
                        .param("usernameOrEmail", "testuser")
                        .param("password", "raw_password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/101"))
                .andExpect(request().sessionAttribute("loggedUser", mockUser));
    }

    // ------------------- POST /login (Fail: User Not Found) -------------------
    @Test
    void login_userNotFound_shouldReturnError() throws Exception {
        // Arrange
        when(loginService.findByUsernameOrEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(post("/login")
                        .param("usernameOrEmail", "wrong_user")
                        .param("password", "any_password"))
                .andExpect(status().isOk())
                .andExpect(view().name("login_page"))
                .andExpect(model().attribute("error", "Invalid username or password"));
    }

    // ------------------- POST /login (Fail: Wrong Password) -------------------
    @Test
    void login_wrongPassword_shouldReturnError() throws Exception {
        // Arrange
        User mockUser = new User();
        mockUser.setPasswordHash("encoded_password");

        when(loginService.findByUsernameOrEmail("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrong_password", "encoded_password")).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/login")
                        .param("usernameOrEmail", "testuser")
                        .param("password", "wrong_password"))
                .andExpect(status().isOk())
                .andExpect(view().name("login_page"))
                .andExpect(model().attribute("error", "Invalid username or password"));
    }
}