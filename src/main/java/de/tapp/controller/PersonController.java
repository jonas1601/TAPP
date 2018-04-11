package de.tapp.controller;

import de.tapp.application.HibernateConfiguration;
import de.tapp.entity.Person;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @GetMapping(path = "/person")
    public Person getPersonById(@RequestParam() int personId) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Person person = session.load(Person.class, personId);
        session.close();
        return person;
    }

    @PostMapping(path = "/person")
    public HttpStatus addPerson(@RequestParam String benutzername, @RequestParam String vorname,
                                @RequestParam String nachname, @RequestParam String pass,
                                @RequestParam String notificationToken) {
        Session session = null;
        try {
            Person person = new Person();
            person.setBenutzername(benutzername);
            person.setVorname(vorname);
            person.setNachname(nachname);
            person.setNotificationToken(notificationToken);
            person.setPass(pass);
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(person);
            session.flush();
            session.close();
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
