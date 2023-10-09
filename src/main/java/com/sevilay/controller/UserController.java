package com.sevilay.controller;

import com.sevilay.repository.entity.User;
import com.sevilay.service.UserService;
import com.sevilay.utility.Constants;
import com.sevilay.utility.HibernateUtility;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;
import java.util.Scanner;

public class UserController {
    EntityManager entityManager;
    CriteriaBuilder criteriaBuilder;
    UserService userService;
    Scanner sc;

    public UserController() {
        this.userService = new UserService();
        this.sc = new Scanner(System.in);
        this.entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public User save(User user) {
        return userService.save(user);
    }

    public User createAccount() {
        System.out.println("*************************************************");
        System.out.println("***************  KAYIT OLUŞTUR ******************");
        System.out.println("*************************************************");
        System.out.println();
        System.out.println("Kullanıcı adınızı giriniz: ");
        String username = sc.nextLine();
        System.out.println("Adınızı ve soyadınızı giriniz: ");
        String fullname = sc.nextLine();
        User user = User.builder()
                .username(username)
                .fullname(fullname)
                .baseEntity(Constants.getBaseEntity())
                .build();

        return userService.save(user);
    }

    public Optional<User> loginUser() {
        System.out.println("Lütfen kullanıcı adınızı giriniz: ");
        String username = sc.nextLine();
        Optional<User> user = userService.usernameGoreKullaniciBul(username);
        if (user.isPresent()) {
            System.out.println("Başarıyla giriş yaptınız!");
        } else {
            System.out.println("Girdiğiniz kullanıcı adı hatalıdır!");
        }
        return user;
    }

    public Optional<User> usernameGoreKullaniciBul(String username) {
        return userService.usernameGoreKullaniciBul(username);
    }


}


