package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Announcement;

import java.util.List;

/**
 * This is the service interface for managing announcement-related  operations.
 *
 * It provides business-level methods for retrieving,
 * and filtering the Announcement entity.
 */

public interface AnnouncementService {
    List<Announcement> findAll();
    void save(Announcement announcement);
    Announcement findById(int id);
    List<Announcement> findActiveAnnouncements();
}
