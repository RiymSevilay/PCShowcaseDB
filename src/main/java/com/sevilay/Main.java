package com.sevilay;

import com.sevilay.controller.*;
import com.sevilay.repository.entity.User;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    Scanner sc;
    UserController userController;
    PostController postController;
    ComputerSpecController computerSpecController;
    ComputerController computerController;
    LikeController likeController;


    public Main() {
        this.sc = new Scanner(System.in);
        this.userController = new UserController();
        this.postController = new PostController();
        this.computerSpecController = new ComputerSpecController();
        this.computerController = new ComputerController();
        this.likeController = new LikeController();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.computerApp();
    }

    public void computerApp() {
        Integer secim = 0;
        do {
            secim = menu();
            switch (secim) {
                case 1: {
                    userController.createAccount();
                    break;
                }
                case 2: {
                    Optional<User> user = loginUser();
                    if (user.isPresent()) {
                        userMenu(user.get());
                    }
                    break;
                }
                case 0: {
                    System.out.println("Çıkış Yapıldı.");
                    break;
                }
            }
        } while (secim != 0);

    }

    private void userMenu(User user) {
        Integer secim = 0;
        do {
            System.out.println("1- Bilgisayar Bileşeni Oluştur");
            System.out.println("2- Bilgisayar Oluştur");
            System.out.println("3- Yeni Post At");
            System.out.println("4- Posta Beğeni Yap");
            System.out.println("5- Tüm Postları Görüntüle");
            System.out.println("6- Beğendiğim Gönderileri Görüntüle");
            System.out.println("7 -Bir Kişiye Ait Postları Listele");
            System.out.println("0- ÇIKIŞ");
            System.out.print("Lütfen seçim yapınız: ");
            secim = Integer.parseInt(sc.nextLine());
            switch (secim) {
                case 1: {
                    computerSpecController.createSpec();
                    break;
                }
                case 2: {
                    computerController.createComputer();
                    break;
                }
                case 3: {
                    postController.newPost();
                    break;
                }
                case 4: {
                    likeController.likePost();
                    break;
                }
                case 5: {
                    postController.findAll().forEach(System.out::println);
                    break;
                }
//                case 6: {
//                myLikePosts();
//                break;
//                }
//                case 7: {
//                findPostsByUserIdNotlama();
//                break;}
                case 0: {
                    break;
                }
            }
        } while (secim != 0);
    }

    private Optional<User> loginUser() {
        System.out.print("Lütfen kullanıcı adınızı giriniz: ");
        String username = sc.nextLine();
        Optional<User> user = userController.usernameGoreKullaniciBul(username);
        if (user.isPresent()) {
            System.out.println("Hoşgeldin " + user.get().getFullname());
        } else {
            System.out.println("Girdiğiniz kullanıcı adı hatalıdır!");
        }
        return user;
    }

    private Integer menu() {
        System.out.println("-------  HOŞGELDİNİZ! -------");
        System.out.println("1- Kullanıcı Oluştur ");
        System.out.println("2- Giriş Yap ");
        System.out.println("0- ÇIKIŞ");
        System.out.println("Lütfen seçim yapınız: ");
        Integer secim = Integer.parseInt(sc.nextLine());
        return secim;
    }
}