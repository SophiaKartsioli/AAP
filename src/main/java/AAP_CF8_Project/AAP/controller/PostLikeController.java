package AAP_CF8_Project.AAP.controller;

import AAP_CF8_Project.AAP.domain.PostLike;
import AAP_CF8_Project.AAP.repository.PostLikeRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class PostLikeController {
    private final PostLikeRepository likeRepo;

    public PostLikeController(PostLikeRepository likeRepo) {
        this.likeRepo = likeRepo;
    }

    @PostMapping("/posts/{id}/like")
    public String toggleLike(@PathVariable Long id) {

        int userId = 1; // fake logged-in user

        likeRepo.findByUserIdAndPostId(userId, id)
                .ifPresentOrElse(
                        likeRepo::delete,
                        () -> {
                            PostLike like = new PostLike();
                            like.setUserId(userId);
                            like.setPostId(id.intValue());
                            likeRepo.save(like);
                        }
                );

        return "redirect:/profile/1";
    }
}
