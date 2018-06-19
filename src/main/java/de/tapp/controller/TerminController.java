package de.tapp.controller;

import de.tapp.entity.Gruppe;
import de.tapp.entity.Termin;
import de.tapp.entity.TerminPerson;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static de.tapp.application.HibernateConfiguration.cleanup;
import static de.tapp.application.HibernateConfiguration.openSession;

@RestController
@CrossOrigin
@Transactional
public class TerminController {

    private GruppenController gruppenController;

    public TerminController(GruppenController gruppenController) {
        this.gruppenController = gruppenController;
    }

    @GetMapping(path = "/stati")
    public List<TerminPerson> getStatiVonPerson(@RequestParam int personId) {
        Session session = null;
        try {
            session = openSession();
            List<TerminPerson> stati = session.createCriteria(TerminPerson.class)
                    .add(Restrictions.eq("personId", personId))
                    .list();
            return stati;
        } finally {
            cleanup(session);
        }
    }

    @GetMapping(path = "/termin")
    public Termin getTerminById(@RequestParam int terminId) {
        Session session = null;
        try {
            session = openSession();
            Termin termin = session.load(Termin.class, terminId);
            return termin;
        } finally {
            cleanup(session);
        }
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
        Session session = null;
        try {
            session = openSession();
            LocalDateTime heutigerTag = LocalDateTime.now();
            heutigerTag = heutigerTag.truncatedTo(ChronoUnit.DAYS);

            List<Termin> termine = session.createCriteria(Termin.class)
                    .add(Restrictions.eq("gruppenId", gruppenId))
                    .add(Restrictions.ge("ende", heutigerTag))
                    .list();
            return termine;
        } finally {
            cleanup(session);
        }
    }

    @PostMapping(path = "/termin")
    public HttpStatus addTermin(@RequestParam int gruppenId, @RequestBody Termin termin) {

        Session session = null;
        try {
            session = openSession();
            termin.setGruppenId(gruppenId);
            session.beginTransaction();
            session.save(termin);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            cleanup(session);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/termin")
    public HttpStatus deleteTermin(@RequestParam() int terminId) {
        Session session = null;
        try {
            session = openSession();
            Termin termin = (Termin) session.load(Termin.class, terminId);
            session.beginTransaction();
            session.delete(termin);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            cleanup(session);
        }
    }

    @PostMapping(path = "/terminperson")
    public TerminPerson setzteTerminStatus(@RequestParam int terminId, @RequestParam int personId, @RequestParam int status, @RequestParam String kommentar) {
        Session session = null;
        try {
            session = openSession();
            TerminPerson s = new TerminPerson();
            s.setTerminId(terminId);
            s.setPersonId(personId);
            s.setStatusId(status);
            s.setKommentar(kommentar);
            s.setDatumAenderung(LocalDateTime.now());
            session.beginTransaction();
            session.saveOrUpdate(s);
            s.setDatumAenderung(null);
            return s;
        } catch (Exception e) {
            return null;
        } finally {
            cleanup(session);
        }
    }

    @GetMapping(path = "/terminstati")
    public List<TerminPerson> getTerminStati(@RequestParam int terminId) {
        Session session = null;
        try {
            session = openSession();
            List<TerminPerson> stati = session.createCriteria(TerminPerson.class)
                    .add(Restrictions.eq("terminId", terminId))
                    .list();
            return stati;
        } catch (Exception e) {
            return null;
        } finally {
            cleanup(session);
        }
    }
}
