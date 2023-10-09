package com.sevilay.controller;

import com.sevilay.repository.entity.Post;
import com.sevilay.service.ComputerService;
import com.sevilay.service.LikeService;
import com.sevilay.service.PostService;
import com.sevilay.service.UserService;
import com.sevilay.utility.Constants;
import com.sevilay.utility.HibernateUtility;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Scanner;


public class PostController {

    PostService postService;
    Scanner sc;
    UserService userService;
    ComputerService computerService;
    LikeService likeService;
    LikeController likeController;
    EntityManager entityManager;
    CriteriaBuilder criteriaBuilder;

    public PostController() {
        this.postService = new PostService();
        this.sc = new Scanner(System.in);
        this.userService = new UserService();
        this.computerService = new ComputerService();
        this.likeService = new LikeService();
        this.likeController = new LikeController();
        this.entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Post newPost() {
        System.out.print("Postunuzun içeriğini yazınız: ");
        String content = sc.nextLine();
        Post post = Post.builder()
                .userid(userService.findById(newPost().getUserid()).get().getId())
                .content(content)
                .computerid(computerService.findById(newPost().getComputerid()).get().getId())
                .postDate(System.currentTimeMillis())
                .baseEntity(Constants.getBaseEntity())
                .build();
        return postService.save(post);
    }

    public List<Post> findAll() {
        return postService.findAll();
    }


}

