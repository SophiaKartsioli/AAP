package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.PostLike;
import org.springframework.data.repository.CrudRepository;

public interface PostLikeService {
       Iterable<PostLike> findAll();
}
