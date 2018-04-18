package de.tapp.controller;

import de.tapp.application.HibernateConfiguration;
import de.tapp.entity.Credentials;
import de.tapp.entity.Person;
import de.tapp.entity.Termin;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PersonController {

    @GetMapping(path = "/person")
    public Person getPersonById(@RequestParam() int personId) {

        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Person person = null;
        try{
             person = session.load(Person.class, personId);

        }catch(ObjectNotFoundException e){

        }finally {
            session.close();
        }

        return person;

    }

    @PostMapping(path = "/login")
    public Person login(@RequestBody Credentials credentials){
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Person person = null;
        try {
           person = (Person) session.createCriteria(Person.class)
                   .add(Restrictions.eq("benutzername", credentials.getUsername()))
                   .add(Restrictions.ge("pass", credentials.getPassword()))
                   .uniqueResult();

       }catch (ObjectNotFoundException e){

        }finally {
            session.close();
        }
       return person;
    }


    @PostMapping(path = "/register")
        public HttpStatus addPerson(@RequestBody Person person) {
            Session session = null;
           /* Person person = new Person();
            person.setBenutzername(benutzername);
            person.setVorname(vorname);
            person.setNachname(nachname);
            person.setNotificationToken(notificationToken);
            person.setPass(pass);*/

            try {
                session = HibernateConfiguration.getSessionFactory().openSession();
                session.beginTransaction();
                person = (Person) session.createCriteria(Person.class)
                        .add(Restrictions.eq("benutzername", person.getBenutzername()));
                return HttpStatus.NOT_FOUND;
            }catch (ObjectNotFoundException e){
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
