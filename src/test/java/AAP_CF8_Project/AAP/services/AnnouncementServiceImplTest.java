package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Announcement;
import AAP_CF8_Project.AAP.repository.AnnouncementRepository;
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

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceImplTest {

    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private AnnouncementServiceImpl announcementService;

    private Announcement activeAnnouncement;
    private Announcement inactiveAnnouncement;

    @BeforeEach
    void setup() {
        activeAnnouncement = new Announcement();
        activeAnnouncement.setId(1);
        activeAnnouncement.setTitle("Active Announcement");
        activeAnnouncement.setActive(true);

        inactiveAnnouncement = new Announcement();
        inactiveAnnouncement.setId(2);
        inactiveAnnouncement.setTitle("Inactive Announcement");
        inactiveAnnouncement.setActive(false);
    }

    // ------------------- findAll -------------------

    @Test
    void findAll_shouldReturnAllAnnouncements() {
        when(announcementRepository.findAll())
                .thenReturn(List.of(activeAnnouncement, inactiveAnnouncement));

        List<Announcement> result = announcementService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(announcementRepository, times(1)).findAll();
    }

    // ------------------- save -------------------

    @Test
    void save_shouldCallRepositorySave() {
        announcementService.save(activeAnnouncement);

        verify(announcementRepository, times(1)).save(activeAnnouncement);
    }

    // ------------------- findById -------------------

    @Test
    void findById_shouldReturnAnnouncement_whenExists() {
        when(announcementRepository.findById(1)).thenReturn(Optional.of(activeAnnouncement));

        Announcement result = announcementService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {
        when(announcementRepository.findById(3)).thenReturn(Optional.empty());

        Announcement result = announcementService.findById(3);

        assertNull(result);
    }

    // ------------------- findActiveAnnouncements -------------------

    @Test
    void findActiveAnnouncements_shouldReturnOnlyActiveAnnouncements() {
        when(announcementRepository.findAll())
                .thenReturn(List.of(activeAnnouncement, inactiveAnnouncement));

        List<Announcement> result = announcementService.findActiveAnnouncements();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isActive());
        assertEquals("Active Announcement", result.get(0).getTitle());

        verify(announcementRepository, times(1)).findAll();
    }
}
