package com.sevilay.repository;

import com.sevilay.repository.entity.Post;
import com.sevilay.utility.MyFactoryRepository;

public class PostRepository extends MyFactoryRepository<Post, Long> {

    public PostRepository() {
        super(new Post());
    }

    public Post update(Post post) {
        return update(post);
    }
}
