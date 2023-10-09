package com.sevilay.repository;

import com.sevilay.repository.entity.Like;
import com.sevilay.utility.MyFactoryRepository;

public class LikeRepository extends MyFactoryRepository<Like, Long> {

    public LikeRepository() {
        super(new Like());
    }
}
