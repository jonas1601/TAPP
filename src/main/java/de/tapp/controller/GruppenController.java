package de.tapp.controller;

import de.tapp.application.HibernateConfiguration;
import de.tapp.entity.Gruppe;
import de.tapp.entity.Gruppenmitglied;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin
@Transactional
public class GruppenController {


    @GetMapping(path = "/gruppe/{id}")
    public List getGruppenMitglieder(@PathVariable(name = "id")int id){

        String select = " FROM Gruppenmitglied gr ";
        Session session = HibernateConfiguration.getSessionFactory().openSession();
      org.hibernate.query.Query query =  session.createQuery(select);
     //  query.setParameter(1,id);

        return query.list();

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
