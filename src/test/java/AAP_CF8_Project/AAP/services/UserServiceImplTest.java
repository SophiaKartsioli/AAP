package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
Without @ExtendWith: (MockitoExtension.class) Annotation
@Mock → null
@InjectMocks → fails
Tests crash with NullPointerException
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    /*
    @Mock Annotation
    Mockito creates a fake implementation of UserRepository
    No database is involved
    Every method returns:null, 0, false, or empty values unless you explicitly define behavior
     */
    @Mock
    private UserRepository userRepository;

    /*
     @InjectMocks Annotation
     Mockito creates a real instance of UserServiceImpl. Then it injects the mocked dependencies (userRepository)
     Injection happens via: constructor (preferred), setter or field (fallback)
     Equivalent manual code UserServiceImpl userService = new UserServiceImpl(mockedUserRepository);
     */
    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    /*
    Why we use @BeforeEach Annotation:
   -Shared test data
   -Clean state before every test
   -Avoids duplication
    Each test:
   -Starts fresh
   -Cannot affect another test
     */
    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1);
        user.setUsername("john");
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        Iterable<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, ((List<User>) result).size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnUser_whenExists() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.findById(1);

        assertEquals(1, result.getId());
        assertEquals("john", result.getUsername());
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.findById(1)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void findByUsername_shouldReturnUser_whenExists() {
        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        User result = userService.findByUsername("john");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    void findByUsername_shouldReturnNull_whenNotExists() {
        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.empty());

        User result = userService.findByUsername("john");

        assertNull(result);
    }

    @Test
    void findAllUsers_shouldReturnList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.findAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("john", users.get(0).getUsername());
    }

    @Test
    void save_shouldCallRepositorySave() {
        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        userService.deleteById(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void existsById_shouldReturnTrue_whenExists() {
        when(userRepository.existsById(1)).thenReturn(true);

        boolean exists = userService.existsById(1);

        assertTrue(exists);
    }

    @Test
    void existsById_shouldReturnFalse_whenNotExists() {
        when(userRepository.existsById(1)).thenReturn(false);

        boolean exists = userService.existsById(1);

        assertFalse(exists);
    }
}
