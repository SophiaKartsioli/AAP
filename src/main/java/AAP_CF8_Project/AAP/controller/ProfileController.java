package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.services.UserService;
import AAP_CF8_Project.AAP.utils.CurrentUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The profile Controller is responsible for displaying all the information that was given form the user during the creation account.
 * The user redirects from the login page to their own personal page.
 * There is the option for the user to edit their profile where they get redirected to the edit profile page where they can change their username, bio, location and website.
 * The Controller handles the submission of the edited profile and display the new information to their profile page.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final PostService postService;
    private final CurrentUser currentUser;

    public ProfileController(UserService userService, PostService postService, CurrentUser currentUser) {
        this.userService = userService;
        this.postService = postService;
        this.currentUser = currentUser;
    }


    @GetMapping("/{id}")
    public String profile(@PathVariable int id, Model model) {
        User user = userService.findById(id);
        if (user == null) return "redirect:/login";

        List<Post> posts = postService.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);

        return "profile_page";
    }

    @GetMapping("/edit")
    public String editProfile(HttpSession session, Model model) {
        User loggedUser = currentUser.get();
        if (loggedUser == null) {
            return "redirect:/login";
        }

        User user = userService.findById(loggedUser.getId());
        model.addAttribute("user", user);

        return "profile_edit";
    }

    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute("user") User updatedUser,
            HttpSession session
    ) {
        User loggedUser = currentUser.get();
        if (loggedUser == null) {
            return "redirect:/login"; // redirect if not logged in
        }

        User user = userService.findById(loggedUser.getId());

        user.setBioText(updatedUser.getBioText());
        user.setLocation(updatedUser.getLocation());
        user.setWebsite(updatedUser.getWebsite());

        userService.save(user); // save changes

        session.setAttribute("loggedUser", user);

        return "redirect:/profile/" + user.getId();
    }

}
