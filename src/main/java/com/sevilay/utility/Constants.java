package com.sevilay.utility;

import com.sevilay.repository.entity.BaseEntity;


public class Constants {

    public static BaseEntity getBaseEntity() {
        return BaseEntity.builder().
                createAt(System.currentTimeMillis())
                .updateAt(System.currentTimeMillis())
                .state(true)
                .build();
    }
}
