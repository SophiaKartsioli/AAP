package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.PostImage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostImageServiceImpl implements PostImageService {

    private Path uploadDir;

    public PostImageServiceImpl(@Value("${app.upload.dir}")String rootLocation){
        initializePath(rootLocation);
    }

    public void initializePath(String rootLocation ) {
        uploadDir = Paths.get(rootLocation);
        try {
            Files.createDirectories(uploadDir);
           // log.info("Upload directory initialized at {}", uploadDir.toAbsolutePath());
        } catch (IOException e) {
           // log.error("Failed to initialize upload directory: {}", uploadDir, e);
            throw new RuntimeException("Could not initialize upload folder.", e);
        }
    }

    @Override
    public PostImage savePostImage(MultipartFile file) {
        try {
            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String extension = getFileExtension(originalFilename);

            String uniqueFilename = UUID.randomUUID() + extension;
            Path destinationFile = uploadDir.resolve(uniqueFilename).normalize();

            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            //log.debug("Stored file {} as {}", originalFilename, uniqueFilename);

            PostImage postImage = new PostImage();
            postImage.setImageUrl("/uploads/" + uniqueFilename);
            postImage.setFileName(uniqueFilename);
            postImage.setContentType(file.getContentType());

            return postImage;

        } catch (IOException e) {
            //log.error("Failed to store file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Failed to store file");
        }
    }

    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }
}
