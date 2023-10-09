package com.sevilay.controller;

import com.sevilay.repository.entity.ComputerSpec;
import com.sevilay.service.ComputerSpecService;
import com.sevilay.utility.Constants;
import com.sevilay.utility.HibernateUtility;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Scanner;

public class ComputerSpecController {

    EntityManager entityManager;
    CriteriaBuilder criteriaBuilder;
    ComputerSpecService computerSpecService;
    Scanner sc;

    public ComputerSpecController() {
        this.computerSpecService = new ComputerSpecService();
        this.sc = new Scanner(System.in);
        this.entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public ComputerSpec createSpec() {
        System.out.println("Bilgisayarın modeli giriniz: ");
        String computerBrand = sc.nextLine();
        System.out.println("Çekirdek sayısını giriniz: ");
        Integer coreCount = Integer.parseInt(sc.nextLine());
        System.out.println("Hafıza kapasiyesini giriniz: ");
        Integer memory = Integer.parseInt(sc.nextLine());
        ComputerSpec computerSpec = ComputerSpec.builder()
                .computerBrand(computerBrand)
                .coreCount(coreCount)
                .memory(memory)
                .baseEntity(Constants.getBaseEntity())
                .build();
        return computerSpecService.save(computerSpec);
    }
}
