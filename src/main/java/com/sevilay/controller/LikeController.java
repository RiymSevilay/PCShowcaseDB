package com.sevilay.controller;

import com.sevilay.repository.entity.Like;
import com.sevilay.repository.entity.User;
import com.sevilay.service.LikeService;
import com.sevilay.service.PostService;
import com.sevilay.service.UserService;
import com.sevilay.utility.Constants;

import java.util.Scanner;

public class LikeController {

    LikeService likeService;
    Scanner sc;
    UserService userService;
    PostService postService;

    public LikeController() {
        this.likeService = new LikeService();
        this.sc = new Scanner(System.in);
        this.userService = new UserService();
        this.postService = new PostService();
    }


    public Like save(Like like) {
        return likeService.save(like);
    }

    public Like likePost() {
        System.out.println("Hangi posta beğeni atmak istersiniz: ");
        Long postid = Long.parseLong(sc.nextLine());
        Like like = Like.builder()
                .userid(userService.findById(likePost().getUserid()).get().getId())
                .postid(postService.postBul(likePost().getPostid()).get().getId())
                .likeDate(System.currentTimeMillis())
                .build();

        return likeService.save(like);

    }

    public Like likePost(User user) {
        postService.findAll().forEach(System.out::println);
        Long id = Long.parseLong("Beğeni atmak istediğiniz post id'sini giriniz: ");
        Like like = Like.builder()
                .userid(user.getId())
                .postid(id)
                .baseEntity(Constants.getBaseEntity())
                .build();
        postService.findById(id).ifPresent(p -> {
            p.setLikeCount(p.getLikeCount() + 1);
            postService.update(p);
        });
        return likeService.save(like);
    }

}
