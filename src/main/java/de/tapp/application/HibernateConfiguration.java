package de.tapp.application;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

public class HibernateConfiguration {


    public static SessionFactory getSessionFactory(){
        Configuration config = new Configuration();
        config.configure();
        return config.buildSessionFactory();
    }
}
