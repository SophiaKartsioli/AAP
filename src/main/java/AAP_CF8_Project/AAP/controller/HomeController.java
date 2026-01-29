package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.AnnouncementService;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.utils.CurrentUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The Controller is responsible to display the posts that are created from the current user and other users as well,
 * with the amount of the displayed posts limited to 2, sorted by create date.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    private final PostService postService;
    private final AnnouncementService announcementService;
    private final CurrentUser currentUser;

    public HomeController(PostService postService,AnnouncementService announcementService, CurrentUser currentUser) {
        this.postService = postService;
        this.announcementService = announcementService;
        this.currentUser = currentUser;
    }

    @GetMapping()
    public String home(
            @RequestParam(value = "page", defaultValue = "0") int page,
            HttpSession session,
            Model model
    ) {

        User loggedUser = currentUser.get();
        if (loggedUser == null) return "redirect:/login";


        Pageable pageable = PageRequest.of(page, 2, Sort.by("createdDate").descending());
        Page<Post> postPage = postService.findAllPosts(pageable);

        model.addAttribute("postPage", postPage);

        model.addAttribute(
                "announcements",
                announcementService.findActiveAnnouncements()
        );

        return "home_page";
    }
}
