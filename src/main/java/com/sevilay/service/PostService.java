package com.sevilay.service;

import com.sevilay.repository.PostRepository;
import com.sevilay.repository.entity.Post;

import java.util.List;
import java.util.Optional;

public class PostService {

    PostRepository postRepository;

    public PostService() {
        this.postRepository = new PostRepository();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> postBul(Long postid) {
        return postRepository.findById(postid);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);

    }

    public Post update(Post post) {
        return postRepository.update(post);
    }
}
