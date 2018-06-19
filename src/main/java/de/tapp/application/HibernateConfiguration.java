package de.tapp.application;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {

    private static SessionFactory factory;

    static {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory(){
        return factory;
    }

    public static void cleanup(Session session) {
        if (session == null) return;
        if (session.getTransaction().isActive())
            session.flush();
        session.close();
    }

    public static Session openSession() {
        return factory.openSession();
    }
}
