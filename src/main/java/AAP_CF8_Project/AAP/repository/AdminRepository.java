package AAP_CF8_Project.AAP.repository;

import AAP_CF8_Project.AAP.domain.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * This is repository interface for managing Admin entity.
 *
 * It provides the CRUD operations and custom queries
 * for accessing to administration data.
 */

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
