package AAP_CF8_Project.AAP.controllers;

import AAP_CF8_Project.AAP.controller.ProfileController;
import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.services.UserService;
import AAP_CF8_Project.AAP.utils.CurrentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfileControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private PostService postService;
    private CurrentUser currentUser;

    private User sampleUser;
    private List<Post> samplePosts;

    @BeforeEach
    void setup() {
        userService = Mockito.mock(UserService.class);
        postService = Mockito.mock(PostService.class);
        currentUser = Mockito.mock(CurrentUser.class);

        ProfileController controller = new ProfileController(userService, postService, currentUser);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Sample user
        sampleUser = new User();
        sampleUser.setId(1);
        sampleUser.setUsername("john");

        // Sample posts
        Post post = new Post();
        post.setId(10L);
        post.setAuthor(sampleUser);
        post.setContentText("Hello world!");
        samplePosts = List.of(post);
    }

    // ------------------- GET /profile/{id} -------------------
    @Test
    void getProfile_shouldReturnProfilePage() throws Exception {
        when(userService.findById(1)).thenReturn(sampleUser);
        when(postService.findByUser(sampleUser)).thenReturn(samplePosts);

        mockMvc.perform(get("/profile/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile_page"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("posts"));

        verify(userService, times(1)).findById(1);
        verify(postService, times(1)).findByUser(sampleUser);
    }

    @Test
    void getProfile_redirectsIfUserNotFound() throws Exception {
        when(userService.findById(anyInt())).thenReturn(null);

        mockMvc.perform(get("/profile/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).findById(999);
        verify(postService, never()).findByUser(any());
    }

    // ------------------- GET /profile/edit -------------------
    @Test
    void getEditProfile_shouldReturnEditPage() throws Exception {
        when(currentUser.get()).thenReturn(sampleUser);
        when(userService.findById(1)).thenReturn(sampleUser);

        mockMvc.perform(get("/profile/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile_edit"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).findById(1);
    }

    @Test
    void getEditProfile_redirectsIfNotLoggedIn() throws Exception {
        when(currentUser.get()).thenReturn(null);

        mockMvc.perform(get("/profile/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, never()).findById(anyInt());
    }

    // ------------------- POST /profile/update -------------------
    @Test
    void postUpdateProfile_shouldUpdateUserAndRedirect() throws Exception {
        when(currentUser.get()).thenReturn(sampleUser);
        when(userService.findById(1)).thenReturn(sampleUser);

        mockMvc.perform(post("/profile/update")
                        .param("bioText", "New bio")
                        .param("location", "Paris")
                        .param("website", "https://example.com")
                        .sessionAttr("loggedUser", sampleUser)
                        .contentType("application/x-www-form-urlencoded"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/1"));

        // Ensure the user object was updated
        assert(sampleUser.getBioText().equals("New bio"));
        assert(sampleUser.getLocation().equals("Paris"));
        assert(sampleUser.getWebsite().equals("https://example.com"));

        verify(userService, times(1)).save(sampleUser);
    }

    @Test
    void postUpdateProfile_redirectsIfNotLoggedIn() throws Exception {
        when(currentUser.get()).thenReturn(null);

        mockMvc.perform(post("/profile/update")
                        .param("bioText", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, never()).save(any());
    }
}
