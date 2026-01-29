package AAP_CF8_Project.AAP.repository;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This is repository interface for managing Post entity.
 *
 * It provides the CRUD operations and custom query methods for
 * listing posts sorted by creation date.
 */

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByAuthorOrderByCreatedDateDesc(User user);
    Page<Post> findAll(Pageable pageable);
}
