package com.sevilay.repository;

import com.sevilay.repository.entity.User;
import com.sevilay.utility.HibernateUtility;
import com.sevilay.utility.MyFactoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class UserRepository extends MyFactoryRepository<User, Long> {

    EntityManager entityManager;
    CriteriaBuilder criteriaBuilder;

    public UserRepository(){
        super(new User());
        this.entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Optional<User> usernameGoreKullaniciBul(String username) {
        User user = null;
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get("username"), username));
        try {
            user = entityManager.createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return Optional.ofNullable(user);

    }


    }

