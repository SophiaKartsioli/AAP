package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.PostImage;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.repository.UserRepository;
import AAP_CF8_Project.AAP.services.PostImageService;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;


@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final PostImageService postImageService;

    // Folder to save uploaded images
    private static final String UPLOAD_DIR = "uploads/";

    public PostController(PostService postService, UserService userService, PostImageService postImageService) {
        this.postService = postService;
        this.userService = userService;
        this.postImageService = postImageService;
    }

    // Show create post page
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "create_post";
    }

    // Handle post submission
    @PostMapping("/create")
    public String createPost(
            HttpSession session,
            @RequestParam("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            // If no user is logged in, redirect to login page
            return "redirect:/login?error=NotLoggedIn";
        }

        Post post = new Post();
        post.setAuthor(loggedUser);
        post.setContentText(content);
        post.setCreatedDate(LocalDateTime.now());

        if (imageFile != null && !imageFile.isEmpty()) {
            PostImage postImage = postImageService.savePostImage(imageFile);
            post.setImage(postImage);
        }

        postService.save(post);

        return "redirect:/profile/" + loggedUser.getId(); // back to profile page
    }
}
