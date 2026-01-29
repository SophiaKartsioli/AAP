package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.PostImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * This is the service interface for managing post image-related operations.
 *
 * It provides business-level methods for handling images uploads and persisting
 * PosiImage entity.
 *
 */

public interface PostImageService {
    PostImage savePostImage(MultipartFile imageFile);

}
