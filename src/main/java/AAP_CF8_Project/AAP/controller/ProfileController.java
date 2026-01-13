package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final PostService postService;

    public ProfileController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


    @GetMapping("/{id}")
    public String profile(@PathVariable int id, Model model) {
        User user = userService.findById(id);
        List<Post> posts = postService.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);

        return "profile_page";
    }

    @GetMapping("/edit")
    public String editProfile(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // redirect if not logged in
        }

        // Optional: refresh from DB
        User user = userService.findById(loggedUser.getId());
        model.addAttribute("user", user);

        return "profile_edit"; // your edit profile HTML
    }

    // Handle form submission for profile update
    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute("user") User updatedUser,
            HttpSession session
    ) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // redirect if not logged in
        }

        // Load the current user from DB
        User user = userService.findById(loggedUser.getId());

        // Update editable fields
        user.setBioText(updatedUser.getBioText());
        user.setLocation(updatedUser.getLocation()); // add location field in User entity
        user.setWebsite(updatedUser.getWebsite());   // add website field in User entity

        userService.save(user); // save changes

        // Update session
        session.setAttribute("loggedUser", user);

        return "redirect:/profile/" + user.getId();
    }

}
