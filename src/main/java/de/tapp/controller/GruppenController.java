package de.tapp.controller;

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

import static de.tapp.application.HibernateConfiguration.cleanup;
import static de.tapp.application.HibernateConfiguration.openSession;

@RestController
@CrossOrigin
@Transactional
public class GruppenController {

    private PersonController personController;

    public GruppenController(PersonController personController) {
        this.personController = personController;
    }

    @GetMapping(path = "/gruppe/{id}")
    public List getGruppenMitglieder(@PathVariable(name = "id") int id) {

        String select = " FROM Gruppenmitglied gr where  gruppenId = ?";
        Session session = openSession();
        org.hibernate.query.Query query = session.createQuery(select);
        query.setParameter(0, id);

        return query.list();
    }

    @GetMapping(path = "/mitglieder")
    public List<Person> getPersonenFromGruppe(@RequestParam int gruppenId) {
        ArrayList<Person> personen = new ArrayList<>();
        List gruppenmitglieder = getGruppenMitglieder(gruppenId);
        for (Object o : gruppenmitglieder) {
            Person p = personController.getPersonById(((Gruppenmitglied) o).getPersonId());
            p.setPassword(null);
            personen.add(p);
        }
        return personen;
    }

    @PostMapping(path = "/gruppe")
    @Transactional
    public Gruppe addGruppe(@RequestParam String name) {
        try {
            if (name.isEmpty() || name == null) return null;
            Gruppe gruppe = new Gruppe();
            gruppe.setName(name);
            saveToDb(gruppe);
            return gruppe;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/gruppenbyname")
    public List<Gruppe> getGruppenVonPerson(@RequestParam String benutzername) {
        Person person = getPersonFromDbBy(benutzername);
        List<Gruppenmitglied> gruppenmitgliedList = getGruppenMitgliederFromDbBy(person.getPersonId());
        return getGruppenFrom(gruppenmitgliedList);
    }

    private Person getPersonFromDbBy(String benutzername) {
        Session session = null;
        try {
            session = openSession();
            Person person = (Person) session.createCriteria(Person.class).add(Restrictions.eq("benutzername", benutzername)).uniqueResult();
            return person;
        } finally {
            cleanup(session);
        }
    }

    @GetMapping(path = "/gruppenbypersonid")
    public List<Gruppe> getGruppenVonPerson(@RequestParam int personId) {
        List<Gruppenmitglied> gruppenmitgliedList = getGruppenMitgliederFromDbBy(personId);
        return getGruppenFrom(gruppenmitgliedList);
    }

    private List<Gruppenmitglied> getGruppenMitgliederFromDbBy(int personId) {
        Session session = null;
        try {
            session = openSession();
            List<Gruppenmitglied> gruppenmitgliedList = session.createCriteria(Gruppenmitglied.class).add(Restrictions.eq("personId", personId)).list();
            return gruppenmitgliedList;
        } finally {
            cleanup(session);
        }
    }

    private List<Gruppe> getGruppenFrom(List<Gruppenmitglied> gruppenmitgliedList) {
        Session session = null;
        try {
            session = openSession();
            List<Gruppe> gruppen = new ArrayList<>();
            for (int i = 0; i < gruppenmitgliedList.size(); i++) {
                Gruppe gruppe = session.load(Gruppe.class, gruppenmitgliedList.get(i).getGruppenId());
                gruppen.add(gruppe);
            }
            return gruppen;
        } finally {
            cleanup(session);
        }
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
            cleanup(session);
        }
    }

    @PostMapping(path = "gruppenmitglieder")
    public HttpStatus addPersonenToGruppe(@RequestParam int gruppenId, @RequestParam int rollenId, @RequestBody List<Person> personen) {
        Session session = null;
        try {
            for (Person p : personen) {
                Gruppenmitglied gruppenmitglied = createGruppenmitgliedWith(p.getPersonId(), gruppenId, rollenId);
                saveToDb(gruppenmitglied);
            }
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            cleanup(session);
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
        Session session = null;
        try {
            session = openSession();
            return session.load(Rolle.class, id);
        } finally {
            cleanup(session);
        }

    }

    private <T> void saveToDb(T object) {
        Session session = openSession();
        try {
            session.beginTransaction();
            session.save(object);
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
        } finally {
            cleanup(session);
        }

    }

    private <T> void deleteFromDb(T objectToDelete) {
        Session session = openSession();
        session.beginTransaction();
        session.delete(objectToDelete);
        cleanup(session);
    }
}
