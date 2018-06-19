package de.tapp.controller;

import de.tapp.entity.Credentials;
import de.tapp.entity.Person;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static de.tapp.application.HibernateConfiguration.cleanup;
import static de.tapp.application.HibernateConfiguration.openSession;

@RestController
@CrossOrigin
public class PersonController {

    @GetMapping(path = "/personen")
    public List<Person> getAllePersonen() {
        Session session = null;
        try {
            session = openSession();
            List<Person> personen = session.createCriteria(Person.class).list();
            personen.forEach(p -> p.setPassword(""));
            return personen;
        } catch (Exception e) {
            return null;
        } finally {
            cleanup(session);
        }
    }

    @GetMapping(path = "/person")
    public Person getPersonById(@RequestParam() int personId) {

        Session session = openSession();
        Person person = null;
        try {
            person = session.load(Person.class, personId);

        } catch (ObjectNotFoundException e) {

        } finally {
            cleanup(session);
        }

        return person;

    }

    @PostMapping(path = "/login")
    public Person login(@RequestBody Credentials credentials) {
        Session session = openSession();
        Person person = null;
        try {
            person = (Person) session.createCriteria(Person.class)
                    .add(Restrictions.eq("benutzername", credentials.getUsername()))
                    .add(Restrictions.ge("password", credentials.getPassword()))
                    .uniqueResult();

        } catch (ObjectNotFoundException e) {

        } finally {
            cleanup(session);
        }
        return person;
    }


    @PostMapping(path = "/register")
    public ResponseEntity<?> addPerson(@RequestBody Person person) {
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            Person personInDb = (Person) session.createCriteria(Person.class)
                    .add(Restrictions.eq("benutzername", person.getBenutzername()))
                    .uniqueResult();
            if (personInDb == null) {
                person.setNotificationToken("TEST");
                session.save(person);
                return new ResponseEntity<>(person, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            cleanup(session);
        }
    }
}
