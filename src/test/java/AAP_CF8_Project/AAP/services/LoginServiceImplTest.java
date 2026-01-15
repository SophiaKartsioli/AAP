package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginServiceImpl loginService;

    private User user;

    private static final String USERNAME = "john";
    private static final String EMAIL = "john@example.com";

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
    }

    @Test
    void findByUsernameOrEmail_shouldReturnUser_whenUsernameMatches() {
        when(userRepository.findByUsernameOrEmail(USERNAME)).thenReturn(Optional.of(user));

        Optional<User> result = loginService.findByUsernameOrEmail(USERNAME);

        assertTrue(result.isPresent());
        assertEquals(USERNAME, result.get().getUsername());
        verify(userRepository, times(1)).findByUsernameOrEmail(USERNAME);
    }

    @Test
    void findByUsernameOrEmail_shouldReturnUser_whenEmailMatches() {
        when(userRepository.findByUsernameOrEmail(EMAIL)).thenReturn(Optional.of(user));

        Optional<User> result = loginService.findByUsernameOrEmail(EMAIL);

        assertTrue(result.isPresent());
        assertEquals(EMAIL, result.get().getEmail());
        verify(userRepository, times(1)).findByUsernameOrEmail(EMAIL);
    }

    @Test
    void findByUsernameOrEmail_shouldReturnEmpty_whenNotFound() {
        when(userRepository.findByUsernameOrEmail("unknown")).thenReturn(Optional.empty());

        Optional<User> result = loginService.findByUsernameOrEmail("unknown");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByUsernameOrEmail("unknown");
    }
}
