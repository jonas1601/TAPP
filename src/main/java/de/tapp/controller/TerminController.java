package de.tapp.controller;

import de.tapp.application.HibernateConfiguration;
import de.tapp.entity.Termin;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin
@Transactional
public class TerminController {

    @GetMapping(path = "/termin")
    public Termin getTermin() {
        System.out.println("works");
        return null;
    }

    @PostMapping(path = "/termin")
    public HttpStatus addTermin() {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Termin t = new Termin();
        t.setTitel("Test");
        Transaction transaction = session.beginTransaction();
        session.save(t);
        session.flush();
        session.close();
        return HttpStatus.ACCEPTED;
    }
}
