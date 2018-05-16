package de.tapp.controller;

import de.tapp.application.HibernateConfiguration;
import de.tapp.entity.Gruppe;
import de.tapp.entity.Termin;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
@CrossOrigin
@Transactional
public class TerminController {

    private GruppenController gruppenController;

    public TerminController(GruppenController gruppenController) {
        this.gruppenController = gruppenController;
    }

    @GetMapping(path = "/termin")
    public Termin getTerminById(@RequestParam int terminId) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Termin termin = session.load(Termin.class, terminId);
        session.close();
        return termin;
    }

    @GetMapping(path = "/terminevonperson")
    public List<Termin> getTermineVonPersonInZukunft(@RequestParam int personId) {
        List<Gruppe> gruppen = this.gruppenController.getGruppenVonPerson(personId);
        List<Termin> termine = new ArrayList<>();
        for (Gruppe gruppe : gruppen) {
            for (Termin termin : getTermineByGruppenId(gruppe.getGruppenId())) {
                termine.add(termin);
            }
        }
        return termine;
    }

    @GetMapping(path = "/termine")
    public List<Termin> getTermineByGruppenId(@RequestParam int gruppenId) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Date heute = new GregorianCalendar().getTime();

        List<Termin> termine = session.createCriteria(Termin.class)
                .add(Restrictions.eq("gruppenId", gruppenId))
                .add(Restrictions.ge("ende", heute))
                .list();
        return termine;
    }

    @PostMapping(path = "/termin")
    public HttpStatus addTermin(@RequestParam() String title, @RequestParam String beschreibung,
                                @RequestParam int ganztaegig, @RequestParam String anfang,
                                @RequestParam String ende, @RequestParam int gruppenId) {

        Session session = null;
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            Termin t = new Termin();
            t.setTitel(title);
            t.setBeschreibung(beschreibung);
            t.setGanztaegig(ganztaegig);
            t.setAnfang(Timestamp.valueOf(anfang));
            t.setEnde(Timestamp.valueOf(ende));
            t.setGruppenId(gruppenId);
            session.beginTransaction();
            session.save(t);
            session.flush();
            session.close();
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            if (session != null)
                session.close();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/termin")
    public HttpStatus deleteTermin(@RequestParam() int terminId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            Termin termin = (Termin) session.load(Termin.class, terminId);
            session.beginTransaction();
            session.delete(termin);
            session.flush();
            session.close();
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            if (session != null)
                session.close();
        }
    }
}
