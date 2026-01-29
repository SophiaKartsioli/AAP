package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * This is the service interface responsible for authentication-related operations.
 *
 * Provides business-level functionality for locating users
 * during the login process using either a username or an email.
 */

public interface PostService {
    Iterable<Post> findAll();
    List<Post> findByUser(User user);
    Post save(Post post);
    Post findById(Long id);
    void deleteById(Long id);
    Page<Post> findAllPosts(Pageable pageable);
    int countAllPosts();
}

