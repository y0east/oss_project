package kr.ac.hufs.ice.ice.controller;

import kr.ac.hufs.ice.ice.dto.PostCreateDto;
import kr.ac.hufs.ice.ice.entity.Post;
import kr.ac.hufs.ice.ice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    //Todo 이거 지워야 됨
    @PostMapping("createPost")
    public Post createPost(@RequestBody PostCreateDto postCreateDto) {
        Post post = new Post();
        post.setTitle(postCreateDto.getTitle());
        post.setContent(postCreateDto.getContent());
        return postService.save(post);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/{id}")

    public Post getPostById(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        return post;
    }
}

