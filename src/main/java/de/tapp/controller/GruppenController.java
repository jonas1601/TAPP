package de.tapp.controller;

import de.tapp.application.HibernateConfiguration;
import de.tapp.entity.Gruppe;
import de.tapp.entity.Gruppenmitglied;
import de.tapp.entity.Person;
import de.tapp.entity.Rolle;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Transactional
public class GruppenController {

    @GetMapping(path = "/gruppe/{id}")
    public List getGruppenMitglieder(@PathVariable(name = "id") int id) {

        String select = " FROM Gruppenmitglied gr ";
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        org.hibernate.query.Query query = session.createQuery(select);
        //  query.setParameter(1,id);

        return query.list();

    }

    @PostMapping(path = "/gruppe")
    @Transactional
    public HttpStatus addGruppe(@RequestParam String name) {
        Session session = null;
        try {
            if (name.isEmpty() || name == null) return HttpStatus.BAD_REQUEST;
            Gruppe gruppe = new Gruppe();
            gruppe.setName(name);
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(gruppe);
            close(session);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @GetMapping(path = "/gruppenbyname")
    public List<Gruppe> getGruppenVonPerson(@RequestParam String benutzername) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Person person = (Person) session.createCriteria(Person.class).add(Restrictions.eq("benutzername", benutzername)).uniqueResult();
        List<Gruppenmitglied> g = session.createCriteria(Gruppenmitglied.class).add(Restrictions.eq("personId", person.getPersonId())).list();
        List<Gruppe> gruppen = new ArrayList<>();
        for (int i = 0; i < g.size(); i++) {
            Gruppe grp = session.load(Gruppe.class, g.get(i).getGruppenId());
            gruppen.add(grp);
        }
        close(session);
        return gruppen;
    }

    @GetMapping(path = "/gruppe")
    public List<Gruppe> getGruppenVonPerson(@RequestParam int personId) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Person person = session.load(Person.class, personId);
        List<Gruppenmitglied> g = session.createCriteria(Gruppenmitglied.class).add(Restrictions.eq("personId", person.getPersonId())).list();
        List<Gruppe> gruppen = new ArrayList<>();
        for (int i = 0; i < g.size(); i++) {
            Gruppe grp = session.load(Gruppe.class, g.get(i).getGruppenId());
            gruppen.add(grp);
        }
        close(session);
        return gruppen;
    }

    @PostMapping(path = "gruppenmitglied")
    public HttpStatus addPersonToGruppe(@RequestParam int personId, @RequestParam int gruppenId, @RequestParam int rollenId) {
        Session session = null;
        try {
            Gruppenmitglied gruppenmitglied = new Gruppenmitglied();
            gruppenmitglied.setPerson(personId);
            gruppenmitglied.setGruppenId(gruppenId);
            session = HibernateConfiguration.getSessionFactory().openSession();
            gruppenmitglied.setRolle(session.load(Rolle.class, rollenId));
            session.beginTransaction();
            session.save(gruppenmitglied);
            close(session);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @DeleteMapping(path = "gruppenmitglied")
    public HttpStatus removePersonFromGruppe(@RequestParam int personId, @RequestParam int gruppenId) {
        Session session = null;
        try {
            session = openSession();
            Gruppenmitglied mitglied = (Gruppenmitglied) session.createCriteria(Gruppenmitglied.class)
                    .add(Restrictions.eq("personId", personId)).add(Restrictions.eq("gruppenId", gruppenId)).uniqueResult();
            session.beginTransaction();
            session.delete(mitglied);
            close(session);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            if (session != null)
                session.close();
        }
    }

    private Session openSession() {
        return HibernateConfiguration.getSessionFactory().openSession();
    }

    private void close(Session session) {
        session.flush();
        session.close();
    }
}
