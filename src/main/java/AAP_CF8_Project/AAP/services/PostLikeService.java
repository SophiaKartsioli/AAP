package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.PostLike;
import AAP_CF8_Project.AAP.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostLikeService {
       Iterable<PostLike> findAll();
}
