package AAP_CF8_Project.AAP.controllers;

import AAP_CF8_Project.AAP.controller.AdminController;
import AAP_CF8_Project.AAP.domain.Admin;
import AAP_CF8_Project.AAP.domain.Announcement;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.AdminService;
import AAP_CF8_Project.AAP.services.AnnouncementService;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest {

    private MockMvc mockMvc;
    private AdminService adminService;
    private UserService userService;
    private PostService postService;
    private AnnouncementService announcementService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        adminService = Mockito.mock(AdminService.class);
        userService = Mockito.mock(UserService.class);
        postService = Mockito.mock(PostService.class);
        announcementService = Mockito.mock(AnnouncementService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);

        AdminController controller = new AdminController(adminService, userService, postService, announcementService, passwordEncoder);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // ------------------- Login Tests -------------------

    @Test
    void adminLogin_Success() throws Exception {
        // Arrange
        Admin admin = new Admin();
        admin.setUsername("adminUser");
        admin.setPassword("hashedPwd");

        when(adminService.findByUsername("adminUser")).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("rawPwd", "hashedPwd")).thenReturn(true);

        // Mocking the static RequestContextHolder to avoid NPE in login method
        try (MockedStatic<RequestContextHolder> utilities = Mockito.mockStatic(RequestContextHolder.class)) {
            ServletRequestAttributes attributes = mock(ServletRequestAttributes.class);
            HttpServletRequest request = mock(HttpServletRequest.class);

            utilities.when(RequestContextHolder::getRequestAttributes).thenReturn(attributes);
            when(attributes.getRequest()).thenReturn(request);

            // Act & Assert
            mockMvc.perform(post("/admin/login")
                            .param("username", "adminUser")
                            .param("password", "rawPwd"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/admin/dashboard"))
                    .andExpect(request().sessionAttribute("loggedAdmin", admin));
        }
    }

    @Test
    void adminLogin_Failure() throws Exception {
        when(adminService.findByUsername(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/admin/login")
                        .param("username", "badAdmin")
                        .param("password", "wrong"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_login"))
                .andExpect(model().attributeExists("error"));
    }

    // ------------------- Dashboard Tests -------------------

    @Test
    void dashboard_Success() throws Exception {
        Admin admin = new Admin();
        List<User> users = List.of(new User(), new User());

        when(userService.findAllUsers()).thenReturn(users);
        when(postService.countAllPosts()).thenReturn(10);

        mockMvc.perform(get("/admin/dashboard")
                        .sessionAttr("loggedAdmin", admin))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_dashboard"))
                .andExpect(model().attribute("totalUsers", 2))
                .andExpect(model().attribute("totalPosts", 10));
    }

    @Test
    void dashboard_RedirectsIfNoSession() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/login"));
    }

    // ------------------- Announcement Tests -------------------

    @Test
    void createAnnouncement_Success() throws Exception {
        Admin admin = new Admin();

        mockMvc.perform(post("/admin/announcement/create")
                        .sessionAttr("loggedAdmin", admin)
                        .param("title", "Holiday")
                        .param("message", "No work tomorrow"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/announcements"));

        verify(announcementService, times(1)).save(any(Announcement.class));
    }

    @Test
    void toggleAnnouncement_Success() throws Exception {
        Announcement announcement = new Announcement();
        announcement.setActive(true);
        when(announcementService.findById(1)).thenReturn(announcement);

        mockMvc.perform(post("/admin/announcement/toggle")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/announcements"));

        assert(!announcement.isActive()); // Checked that true toggled to false
        verify(announcementService).save(announcement);
    }
}