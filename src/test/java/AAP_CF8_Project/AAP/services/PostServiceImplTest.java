package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private User author;
    private Post post;

    private static final Long POST_ID = 10L;
    private static final int PAGE_SIZE = 10;

    @BeforeEach
    void setUp() {
        // Create reusable author
        author = new User();
        author.setId(1);
        author.setUsername("john");

        // Create reusable post
        post = new Post();
        post.setId(POST_ID);
        post.setAuthor(author);
        post.setContentText("Hello World");
        post.setCreatedDate(LocalDateTime.now());
        post.setImage(null); // optional
    }

    // ------------------- CRUD Tests -------------------

    @Test
    @DisplayName("findAll should return all posts")
    void findAll_shouldReturnAllPosts() {
        when(postRepository.findAll()).thenReturn(List.of(post));

        Iterable<Post> result = postService.findAll();

        assertNotNull(result);
        assertEquals(1, ((List<Post>) result).size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById should return post when it exists")
    void findById_shouldReturnPost_whenExists() {
        when(postRepository.findById(POST_ID)).thenReturn(Optional.of(post));

        Post result = postService.findById(POST_ID);

        assertNotNull(result);
        assertEquals(POST_ID, result.getId());
    }

    @Test
    @DisplayName("findById should return null when post does not exist")
    void findById_shouldReturnNull_whenNotExists() {
        when(postRepository.findById(POST_ID)).thenReturn(Optional.empty());

        Post result = postService.findById(POST_ID);

        assertNull(result);
    }

    @Test
    @DisplayName("save should call repository and return post")
    void save_shouldCallRepositoryAndReturnPost() {
        when(postRepository.save(post)).thenReturn(post);

        Post saved = postService.save(post);

        assertNotNull(saved);
        assertEquals(POST_ID, saved.getId());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    @DisplayName("deleteById should call repository delete")
    void deleteById_shouldCallRepositoryDelete() {
        postService.deleteById(POST_ID);

        verify(postRepository, times(1)).deleteById(POST_ID);
    }

    // ------------------- Filtering Tests -------------------

    @Test
    @DisplayName("findByUser should return posts for given author")
    void findByUser_shouldReturnPostsForUser() {
        when(postRepository.findByAuthorOrderByCreatedDateDesc(author))
                .thenReturn(List.of(post));

        List<Post> result = postService.findByUser(author);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hello World", result.get(0).getContentText());
        verify(postRepository, times(1)).findByAuthorOrderByCreatedDateDesc(author);
    }

    // ------------------- Pagination Tests -------------------

    @Test
    @DisplayName("findAllPosts should return paged posts")
    void findAllPosts_shouldReturnPagedPosts() {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<Post> page = new PageImpl<>(List.of(post));

        when(postRepository.findAll(pageable)).thenReturn(page);

        Page<Post> result = postService.findAllPosts(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Hello World", result.getContent().get(0).getContentText());
        verify(postRepository, times(1)).findAll(pageable);
    }

    // ------------------- Count Tests -------------------

    @Test
    @DisplayName("countAllPosts should return correct count")
    void countAllPosts_shouldReturnCorrectCount() {
        when(postRepository.count()).thenReturn(5L);

        int count = postService.countAllPosts();

        assertEquals(5, count);
        verify(postRepository, times(1)).count();
    }
}
