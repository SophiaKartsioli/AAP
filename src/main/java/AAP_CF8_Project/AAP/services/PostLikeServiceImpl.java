package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Post;
import AAP_CF8_Project.AAP.domain.PostLike;
import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.repository.PostLikeRepository;

import java.util.List;

public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public PostLikeServiceImpl(PostLikeRepository postLikeRepository) {this.postLikeRepository = postLikeRepository;}

    @Override
    public Iterable<PostLike> findAll(){return postLikeRepository.findAll();}
}
