package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.PostImage;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.PostImageService;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.services.UserService;
import AAP_CF8_Project.AAP.utils.CurrentUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * This Controller is responsible for creating and uploading a post from the user.
 * It displays the post creation page where the controller is also responsible to handle the submission of the posts.
 * The posts may contain text and images, which are saved to a folder, and the creating date is submitted as well.
 */
@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final PostImageService postImageService;
    private final CurrentUser currentUser;

    private static final String UPLOAD_DIR = "uploads/";

    public PostController(PostService postService, UserService userService, PostImageService postImageService, CurrentUser currentUser) {
        this.postService = postService;
        this.userService = userService;
        this.postImageService = postImageService;
        this.currentUser = currentUser;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "create_post";
    }

    @PostMapping("/create")
    public String createPost(
            HttpSession session,
            @RequestParam("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        User loggedUser = currentUser.get();
        if (loggedUser == null) {
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

        return "redirect:/profile/" + loggedUser.getId();
    }
}
