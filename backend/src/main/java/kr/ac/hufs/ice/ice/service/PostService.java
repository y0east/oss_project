package kr.ac.hufs.ice.ice.service;
import kr.ac.hufs.ice.ice.entity.Post;

import java.util.List;

public interface PostService {
    Post save(Post post);
    List<Post> findAll();
    Post findById(Long id);
}

