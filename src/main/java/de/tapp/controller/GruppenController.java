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

        String select = " FROM Gruppenmitglied gr where  gruppenId = ?";
        Session session = openSession();
        org.hibernate.query.Query query = session.createQuery(select);
        query.setParameter(0, id);

        return query.list();

    }

    @PostMapping(path = "/gruppe")
    @Transactional
    public HttpStatus addGruppe(@RequestParam String name) {
        try {
            if (name.isEmpty() || name == null) return HttpStatus.BAD_REQUEST;
            Gruppe gruppe = new Gruppe();
            gruppe.setName(name);
            saveToDb(gruppe);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping(path = "/gruppenbyname")
    public List<Gruppe> getGruppenVonPerson(@RequestParam String benutzername) {
        Person person = getPersonFromDbBy(benutzername);
        List<Gruppenmitglied> gruppenmitgliedList = getGruppenMitgliederFromDbBy(person.getPersonId());
        return getGruppenFrom(gruppenmitgliedList);
    }

    private Person getPersonFromDbBy(String benutzername) {
        Session session = openSession();
        Person person = (Person) session.createCriteria(Person.class).add(Restrictions.eq("benutzername", benutzername)).uniqueResult();
        session.close();
        return person;
    }

    @GetMapping(path = "/gruppenbypersonid")
    public List<Gruppe> getGruppenVonPerson(@RequestParam int personId) {
        List<Gruppenmitglied> gruppenmitgliedList = getGruppenMitgliederFromDbBy(personId);
        return getGruppenFrom(gruppenmitgliedList);
    }

    private List<Gruppenmitglied> getGruppenMitgliederFromDbBy(int personId) {
        Session session = openSession();
        List<Gruppenmitglied> gruppenmitgliedList = session.createCriteria(Gruppenmitglied.class).add(Restrictions.eq("personId", personId)).list();
        session.close();
        return gruppenmitgliedList;
    }

    private List<Gruppe> getGruppenFrom(List<Gruppenmitglied> gruppenmitgliedList) {
        Session session = openSession();
        List<Gruppe> gruppen = new ArrayList<>();
        for (int i = 0; i < gruppenmitgliedList.size(); i++) {
            Gruppe gruppe = session.load(Gruppe.class, gruppenmitgliedList.get(i).getGruppenId());
            gruppen.add(gruppe);
        }
        session.close();
        return gruppen;
    }

    @PostMapping(path = "gruppenmitglied")
    public HttpStatus addPersonToGruppe(@RequestParam int personId, @RequestParam int gruppenId, @RequestParam int rollenId) {
        Session session = null;
        try {
            Gruppenmitglied gruppenmitglied = createGruppenmitgliedWith(personId, gruppenId, rollenId);
            saveToDb(gruppenmitglied);
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

    private Gruppenmitglied createGruppenmitgliedWith(int personId, int gruppenId, int rollenId) {
        Gruppenmitglied gruppenmitglied = new Gruppenmitglied();
        gruppenmitglied.setGruppenId(gruppenId);
        gruppenmitglied.setPersonId(personId);
        Rolle rolle = getRoleBy(rollenId);
        gruppenmitglied.setRolle(rolle);
        return gruppenmitglied;
    }

    private Rolle getRoleBy(int id) {
        Session session = openSession();
        return session.load(Rolle.class, id);
    }

    private <T> void saveToDb(T object) {
        Session session = openSession();
        try {
            session.beginTransaction();
            session.save(object);
        } catch (Exception e) {
            throw e;
        } finally {
            cleanup(session);
        }

    }

    @DeleteMapping(path = "gruppenmitglied")
    public HttpStatus removePersonFromGruppe(@RequestParam int personId, @RequestParam int gruppenId) {
        try {
            Gruppenmitglied gruppenmitglied = getGruppenmitgliedFromGroupBy(personId, gruppenId);
            deleteFromDb(gruppenmitglied);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private Gruppenmitglied getGruppenmitgliedFromGroupBy(int personId, int gruppenId) {
        Session session = openSession();
        try {
            return (Gruppenmitglied) session.createCriteria(Gruppenmitglied.class)
                    .add(Restrictions.eq("personId", personId)).add(Restrictions.eq("gruppenId", gruppenId)).uniqueResult();
        } catch (Exception e) {
            throw e;
        }
    }

    private <T> void deleteFromDb(T objectToDelete) {
        Session session = openSession();
        session.beginTransaction();
        session.delete(objectToDelete);
        cleanup(session);
    }

    private Session openSession() {
        return HibernateConfiguration.getSessionFactory().openSession();
    }

    private void cleanup(Session session) {
        session.flush();
        session.close();
    }
}
