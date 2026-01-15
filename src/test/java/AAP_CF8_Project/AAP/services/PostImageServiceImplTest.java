package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.PostImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class PostImageServiceImplTest {

    private PostImageServiceImpl postImageService;

    @TempDir
    Path tempUploadDir; // temporary folder for testing

    @BeforeEach
    void setup() {
        // inject the temp directory as the rootLocation
        postImageService = new PostImageServiceImpl(tempUploadDir.toString()); // simulate @PostConstruct
    }

    @Test
    void savePostImage_shouldStoreFileAndReturnPostImage() throws IOException {
        // Create a fake multipart file
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.png",
                "image/png",
                "fake image content".getBytes()
        );

        PostImage result = postImageService.savePostImage(file);

        // Assert the returned PostImage fields
        assertNotNull(result);
        assertNotNull(result.getFileName());
        assertNotNull(result.getImageUrl());
        assertEquals("image/png", result.getContentType());

        // Assert the file was actually created in temp directory
        Path storedFile = tempUploadDir.resolve(result.getFileName());
        assertTrue(Files.exists(storedFile));
        assertEquals("fake image content", Files.readString(storedFile));
    }

    @Test
    void savePostImage_shouldPreserveFileExtension() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "photo.jpeg",
                "image/jpeg",
                "dummy".getBytes()
        );

        PostImage result = postImageService.savePostImage(file);

        assertTrue(result.getFileName().endsWith(".jpeg"));
        assertTrue(result.getImageUrl().endsWith(result.getFileName()));
    }

    @Test
    void savePostImage_shouldThrowRuntimeException_onIOException() {
        // Create a MockMultipartFile that will throw IOException on getInputStream
        MockMultipartFile file = new MockMultipartFile("file", "fail.png", "image/png", new byte[0]) {
            @Override
            public java.io.InputStream getInputStream() throws IOException {
                throw new IOException("Simulated IO failure");
            }
        };

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> postImageService.savePostImage(file));

        assertEquals("Failed to store file", exception.getMessage());
    }
}
