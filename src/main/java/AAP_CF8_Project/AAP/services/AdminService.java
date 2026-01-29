package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Admin;

import java.util.Optional;

/**
 * This is the service interface for managing administration-related operations.
 *
 * It provides business-level methods for retrieving and persisting Admin entity.
 */

public interface AdminService {
    Optional<Admin> findByUsername(String username);
    Admin save(Admin admin);
}
