package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.User;

import java.util.List;

/**
 * This is the service interface for managing user-related operations.
 *
 * It provides business-level methods for retrieving, persisting and managing Admin entity.
 */

public interface UserService {
    Iterable<User> findAll();

    User findById(int id);
    User findByUsername(String username);
    List<User> findAllUsers();
    void save(User user);
    void deleteById(int id);
    boolean existsById(int id);
}
