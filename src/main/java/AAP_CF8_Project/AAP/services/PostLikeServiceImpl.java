package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.PostLike;
import AAP_CF8_Project.AAP.repository.PostLikeRepository;

public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public PostLikeServiceImpl(PostLikeRepository postLikeRepository) {this.postLikeRepository = postLikeRepository;}

    @Override
    public Iterable<PostLike> findAll(){return postLikeRepository.findAll();}
}
