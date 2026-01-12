package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.PostImage;
import org.springframework.web.multipart.MultipartFile;

public interface PostImageService {
    PostImage savePostImage(MultipartFile imageFile);

}
