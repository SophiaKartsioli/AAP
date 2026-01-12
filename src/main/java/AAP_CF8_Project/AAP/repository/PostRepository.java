package AAP_CF8_Project.AAP.repository;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByAuthor_IdAndForumThreadIsNullOrderByCreatedDateDesc(int userId);

    List<Post> findByForumThread_IdOrderByCreatedDateAsc(Long threadId);
    List<Post> findByAuthorOrderByCreatedDateDesc(User user);

}
