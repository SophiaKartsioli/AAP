package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Iterable<Post> findAll();
    List<Post> findByUser(User user);
    Post save(Post post);
    Post findById(Long id);
    void deleteById(Long id);
    Page<Post> findAllPosts(Pageable pageable);
    int countAllPosts();
}

