package AAP_CF8_Project.AAP.controllers;

import AAP_CF8_Project.AAP.controller.PostController;
import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.PostImage;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.PostImageService;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.services.UserService;
import AAP_CF8_Project.AAP.utils.CurrentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest {

    private MockMvc mockMvc;
    private PostService postService;
    private UserService userService;
    private PostImageService postImageService;
    private CurrentUser currentUser;

    private User sampleUser;

    @BeforeEach
    void setup() {
        // Manual Mocking (No Spring context required)
        postService = Mockito.mock(PostService.class);
        userService = Mockito.mock(UserService.class);
        postImageService = Mockito.mock(PostImageService.class);
        currentUser = Mockito.mock(CurrentUser.class);

        PostController controller = new PostController(postService, userService, postImageService, currentUser);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        sampleUser = new User();
        sampleUser.setId(1);
        sampleUser.setUsername("testuser");
    }

    // ------------------- GET /posts/create -------------------
    @Test
    void showCreateForm_shouldReturnCreatePage() throws Exception {
        mockMvc.perform(get("/posts/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_post"))
                .andExpect(model().attributeExists("post"));
    }

    // ------------------- POST /posts/create -------------------
    @Test
    void createPost_shouldSavePostAndRedirect() throws Exception {
        // Arrange
        when(currentUser.get()).thenReturn(sampleUser);

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "image-content".getBytes()
        );

        PostImage mockSavedImage = new PostImage();
        when(postImageService.savePostImage(any())).thenReturn(mockSavedImage);

        // Act & Assert
        mockMvc.perform(multipart("/posts/create")
                        .file(file)
                        .param("content", "This is a new post!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/1"));

        // Verify services were called
        verify(postImageService, times(1)).savePostImage(any());
        verify(postService, times(1)).save(any(Post.class));
    }

    @Test
    void createPost_shouldRedirectToLoginIfNotLoggedIn() throws Exception {
        // Arrange
        when(currentUser.get()).thenReturn(null);

        // Act & Assert
        mockMvc.perform(multipart("/posts/create")
                        .param("content", "Unauthorized post"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=NotLoggedIn"));

        // Verify save was never called
        verify(postService, never()).save(any());
    }

    @Test
    void createPost_shouldWorkWithoutImage() throws Exception {
        // Arrange
        when(currentUser.get()).thenReturn(sampleUser);

        // Act & Assert
        mockMvc.perform(multipart("/posts/create")
                        .param("content", "Text only post"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/1"));

        // Verify image service was NOT called, but post service WAS
        verify(postImageService, never()).savePostImage(any());
        verify(postService, times(1)).save(any(Post.class));
    }
}