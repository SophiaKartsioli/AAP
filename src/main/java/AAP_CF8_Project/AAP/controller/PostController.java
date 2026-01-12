package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.PostImage;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.repository.UserRepository;
import AAP_CF8_Project.AAP.services.PostImageService;
import AAP_CF8_Project.AAP.services.PostService;
import AAP_CF8_Project.AAP.services.UserService;
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
            @RequestParam("userId") int userId,
            @RequestParam("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        User user = userService.findById(userId);
        if (user == null) {
            return "redirect:/profile/" + userId + "?error=UserNotFound";
        }

        Post post = new Post();
        post.setAuthor(user);
        post.setContentText(content);
        post.setCreatedDate(LocalDateTime.now());

        if (imageFile != null && !imageFile.isEmpty()) {
            PostImage postImage = postImageService.savePostImage(imageFile);
            post.setImage(postImage);
        }

        postService.save(post);

        return "redirect:/profile/" + userId; // back to profile page
    }
}
