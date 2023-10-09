package com.sevilay.controller;

import com.sevilay.repository.entity.Computer;
import com.sevilay.service.ComputerService;
import com.sevilay.service.ComputerSpecService;
import com.sevilay.service.UserService;
import com.sevilay.utility.Constants;
import com.sevilay.utility.HibernateUtility;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Scanner;


public class ComputerController {

    ComputerService computerService;
    Scanner sc;
    UserService userService;

    ComputerSpecService computerSpecService;

    EntityManager entityManager;
    CriteriaBuilder criteriaBuilder;

    public ComputerController() {
        this.computerService = new ComputerService();
        this.sc = new Scanner(System.in);
        this.userService = new UserService();
        this.computerSpecService = new ComputerSpecService();
        this.entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Computer createComputer() {
        System.out.println("************************************************");
        System.out.println("************* BİLGİSAYAR OLUŞTUR ***************");
        System.out.println("************************************************");
        System.out.println();
        System.out.println("Bilgisayarın modelini giriniz: ");
        String model = sc.nextLine();
        System.out.println("Bilgisayara ait resimlerin url adresini ekleyiniz: ");
        String imageurl = sc.nextLine();
        Computer computer = Computer.builder()
                .model(model)
                .imageurl(imageurl)
                .userid(userService.findById(createComputer().getUserid()).get().getId())
                .computerSpecid(computerSpecService.findById(createComputer().getComputerSpecid()).get().getId())
                .baseEntity(Constants.getBaseEntity())
                .build();
        return computerService.save(computer);
    }
}
