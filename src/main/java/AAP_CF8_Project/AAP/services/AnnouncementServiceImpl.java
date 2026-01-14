package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Announcement;
import AAP_CF8_Project.AAP.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository repo;

    public AnnouncementServiceImpl(AnnouncementRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Announcement> findAll() {
        List<Announcement> announcements = new ArrayList<>();
        repo.findAll().forEach(announcements::add); // convert Iterable to List
        return announcements;
    }

    @Override
    public void save(Announcement announcement) {
        repo.save(announcement);
    }

    @Override
    public Announcement findById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Announcement> findActiveAnnouncements() {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
                .filter(Announcement::isActive)
                .toList();
    }

}
