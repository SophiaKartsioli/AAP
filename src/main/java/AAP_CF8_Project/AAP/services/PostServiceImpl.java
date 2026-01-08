package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.repository.PostRepository;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }
}
