package AAP_CF8_Project.AAP.repository;

import AAP_CF8_Project.AAP.domain.Announcement;
import org.springframework.data.repository.CrudRepository;

/**
 * This is repository interface for managing Announcement entity.
 *
 * It provides the CRUD operations for accessing and
 * persisting announcement data.
 */

public interface AnnouncementRepository extends CrudRepository<Announcement, Integer> {
}
