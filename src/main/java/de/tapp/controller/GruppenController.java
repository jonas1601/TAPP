package de.tapp.controller;

import de.tapp.application.HibernateConfiguration;
import de.tapp.entity.Gruppe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin
@Transactional
public class GruppenController {


    @GetMapping(path = "/gruppen")
    public Gruppe getGruppe(){
        return new Gruppe();
    }

    @PostMapping(path = "/add/gruppe")
    @Transactional
    public void addGruppe(){
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Gruppe gruppe = new Gruppe();
        gruppe.setName("Test");
        Transaction transaction = session.beginTransaction();
        session.save(gruppe);
        session.flush();
        session.close();
    }
}
