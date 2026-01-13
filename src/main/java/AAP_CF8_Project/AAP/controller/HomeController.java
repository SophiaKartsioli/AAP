package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.PostService;
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

@Controller
@RequestMapping("/home")
public class HomeController {

    private PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public String home(
            @RequestParam(value = "page", defaultValue = "0") int page,
            HttpSession session,
            Model model
    ) {

        // Get the logged-in user from session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("user", loggedUser);
        }

        // Pageable with 5 posts per page, sorted by createdDate descending
        Pageable pageable = PageRequest.of(page, 2, Sort.by("createdDate").descending());
        Page<Post> postPage = postService.findAllPosts(pageable);

        model.addAttribute("postPage", postPage);
        return "home_page";
    }
}
