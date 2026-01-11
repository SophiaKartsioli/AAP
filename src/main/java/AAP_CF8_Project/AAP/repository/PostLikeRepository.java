package AAP_CF8_Project.AAP.repository;

import AAP_CF8_Project.AAP.domain.PostLike;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostLikeRepository extends CrudRepository<PostLike, Integer> {
    Optional<PostLike> findByUserIdAndPostId(int userId, Long postId);

    int countByPostId(Long postId);
}
