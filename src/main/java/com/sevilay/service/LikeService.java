package com.sevilay.service;

import com.sevilay.repository.LikeRepository;
import com.sevilay.repository.entity.Like;
import com.sevilay.repository.entity.Post;

public class LikeService {

    LikeRepository likeRepository;

    public LikeService() {
        this.likeRepository = new LikeRepository();
    }

    public Like save(Like like) {
        return likeRepository.save(like);
    }
}
