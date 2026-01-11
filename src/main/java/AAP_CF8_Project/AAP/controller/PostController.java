package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.repository.PostRepository;
import AAP_CF8_Project.AAP.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;


@Controller
public class PostController {
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public PostController(PostRepository postRepo, UserRepository userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/posts/create")
    public String createPostForm() {
        return "create-post";
    }

    @PostMapping("/posts")
    public String createPost(
            @RequestParam(required = false) String contentText) {

        if (contentText == null || contentText.trim().isEmpty()) {
            return "redirect:/posts/create";
        }

        User author = userRepo.findById(1)
                .orElseThrow(); // fake logged-in user

        Post post = new Post();
        post.setAuthor(author);
        post.setContentText(contentText);
        post.setCreatedDate(LocalDateTime.now());
        post.setThread(null); // PROFILE POST

        postRepo.save(post);

        return "redirect:/profile/1";
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postRepo.deleteById(id);
        return "redirect:/profile/1";
    }
}
