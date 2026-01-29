package AAP_CF8_Project.AAP.repository;

import AAP_CF8_Project.AAP.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * This is repository interface for managing User entity.
 *
 * It provides the CRUD operations and custom query methods for
 * accessing to user data by email or username.
 */


public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    default Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> user = findByUsername(usernameOrEmail);
        return user.isPresent() ? user : findByEmail(usernameOrEmail);
    }
}
