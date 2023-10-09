package com.sevilay.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtility {

    private static final SessionFactory SESSION;

    static {
        try {
            //  SESSION= new Configuration().configure("c:\\config\\hibernate.cfg.xml").buildSessionFactory();
            SESSION = new Configuration().configure().buildSessionFactory();
        } catch (Exception exception) {
            System.out.println("Hibernate başlatılırken hata oluştu: " + exception);
            throw new ExceptionInInitializerError(exception);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION;
    }

}
