package AAP_CF8_Project.AAP.controllers;

import AAP_CF8_Project.AAP.controller.HomeController;
import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.AnnouncementService;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.utils.CurrentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HomeControllerTest {

    private MockMvc mockMvc;
    private PostService postService;
    private AnnouncementService announcementService;
    private CurrentUser currentUser;

    @BeforeEach
    void setup() {
        postService = Mockito.mock(PostService.class);
        announcementService = Mockito.mock(AnnouncementService.class);
        currentUser = Mockito.mock(CurrentUser.class);

        HomeController controller = new HomeController(postService, announcementService, currentUser);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void home_shouldReturnHomePageWithData() throws Exception {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1);
        when(currentUser.get()).thenReturn(mockUser);

        // Mocking a Page of Posts
        Post post = new Post();
        post.setContentText("Home feed post");
        Page<Post> mockPage = new PageImpl<>(List.of(post));

        when(postService.findAllPosts(any(Pageable.class))).thenReturn(mockPage);
        when(announcementService.findActiveAnnouncements()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/home")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("home_page"))
                .andExpect(model().attributeExists("postPage"))
                .andExpect(model().attributeExists("announcements"));

        verify(postService, times(1)).findAllPosts(any(Pageable.class));
        verify(announcementService, times(1)).findActiveAnnouncements();
    }

    @Test
    void home_shouldRedirectToLoginIfNotAuthenticated() throws Exception {
        // Arrange
        when(currentUser.get()).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Ensure services are never called if user is null
        verify(postService, never()).findAllPosts(any());
        verify(announcementService, never()).findActiveAnnouncements();
    }

    @Test
    void home_shouldHandleCustomPageParameter() throws Exception {
        // Arrange
        when(currentUser.get()).thenReturn(new User());
        Page<Post> emptyPage = new PageImpl<>(List.of());
        when(postService.findAllPosts(any(Pageable.class))).thenReturn(emptyPage);

        // Act
        mockMvc.perform(get("/home").param("page", "5"))
                .andExpect(status().isOk());

        // Assert: Capture the Pageable passed to the service to check if page is 5
        verify(postService).findAllPosts(argThat(pageable -> pageable.getPageNumber() == 5));
    }
}