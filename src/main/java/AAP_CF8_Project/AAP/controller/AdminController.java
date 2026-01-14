package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.Admin;
import AAP_CF8_Project.AAP.domain.Announcement;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.AdminService;
import AAP_CF8_Project.AAP.services.AnnouncementService;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final PostService postService;
    private final AnnouncementService announcementService;

    public AdminController(AdminService adminService,UserService userService,PostService postService,AnnouncementService announcementService) {
        this.adminService = adminService;
        this.userService = userService;
        this.postService = postService;
        this.announcementService = announcementService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "admin_login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Admin admin = adminService.findByUsername(username).orElse(null);

        if (admin == null || !admin.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid username or password");
            return "admin_login";
        }

        // Save admin in session
        session.setAttribute("loggedAdmin", admin);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        // Fetch all users
        List<User> allUsers = userService.findAllUsers();

        // Total users
        int totalUsers = allUsers.size();

        // Total posts
        int totalPosts = postService.countAllPosts();

        // Add to model
        model.addAttribute("loggedAdmin", admin); // for navbar/admin info
        model.addAttribute("users", allUsers);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalPosts", totalPosts);

        return "admin_dashboard"; // your admin page HTML
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/users")
    public String usersPage(HttpSession session, Model model) {

        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("loggedAdmin", admin);
        model.addAttribute("users", userService.findAll());

        return "admin/users";
    }

    @PostMapping("/announcement/create")
    public String createAnnouncement(@RequestParam String title,
                                     @RequestParam String message,
                                     HttpSession session) {

        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        Announcement announcement = new Announcement(title, message, admin);
        announcementService.save(announcement);

        return "redirect:/admin/announcements";
    }

    @GetMapping("/announcements")
    public String announcementsPage(HttpSession session, Model model) {

        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("loggedAdmin", admin);
        model.addAttribute("announcements", announcementService.findAll());

        return "admin/announcement";
    }

    @PostMapping("/announcement/toggle")
    public String toggleAnnouncement(@RequestParam int id) {

        Announcement announcement = announcementService.findById(id);
        if (announcement != null) {
            announcement.setActive(!announcement.isActive());
            announcementService.save(announcement);
        }

        return "redirect:/admin/announcements";
    }


}
