package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Announcement;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> findAll();
    void save(Announcement announcement);
    Announcement findById(int id);
    List<Announcement> findActiveAnnouncements();
}
