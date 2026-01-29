package AAP_CF8_Project.AAP.repository;

import AAP_CF8_Project.AAP.domain.PostImage;
import org.springframework.data.repository.CrudRepository;

/**
 * This is repository interface for managing PostImage entity.
 *
 * It provides the CRUD operations for accessing and
 * persisting image data.
 */

public interface PostImageRepositoty extends CrudRepository<PostImage, Long> {
}
