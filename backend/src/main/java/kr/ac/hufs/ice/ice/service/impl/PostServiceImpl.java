package kr.ac.hufs.ice.ice.service.impl;

import kr.ac.hufs.ice.ice.entity.Post;
import kr.ac.hufs.ice.ice.exception.PostNotFoundException;
import kr.ac.hufs.ice.ice.repository.PostRepository;
import kr.ac.hufs.ice.ice.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post findById(Long id) {
        return postRepository.findById(id)
                .map(post -> {
                    post.incrementViewCount();
                    return post;
                })
                .orElseThrow(PostNotFoundException::new);
    }
}
