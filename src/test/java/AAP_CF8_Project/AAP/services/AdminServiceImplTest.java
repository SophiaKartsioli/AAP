package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Admin;
import AAP_CF8_Project.AAP.repository.AdminRepository;
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
class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;

    private static final String USERNAME = "adminUser";

    @BeforeEach
    void setup() {
        admin = new Admin();
        admin.setId(1l);
        admin.setUsername(USERNAME);
        admin.setPassword("securePassword");
    }

    @Test
    void findByUsername_shouldReturnAdmin_whenExists() {
        when(adminRepository.findByUsername(USERNAME)).thenReturn(Optional.of(admin));

        Optional<Admin> result = adminService.findByUsername(USERNAME);

        assertTrue(result.isPresent());
        assertEquals(USERNAME, result.get().getUsername());
        verify(adminRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    void findByUsername_shouldReturnEmpty_whenNotExists() {
        when(adminRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        Optional<Admin> result = adminService.findByUsername("unknown");

        assertFalse(result.isPresent());
        verify(adminRepository, times(1)).findByUsername("unknown");
    }

    @Test
    void save_shouldCallRepositoryAndReturnAdmin() {
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin saved = adminService.save(admin);

        assertNotNull(saved);
        assertEquals(USERNAME, saved.getUsername());
        verify(adminRepository, times(1)).save(admin);
    }
}
