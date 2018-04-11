package de.tapp.controller;

import de.tapp.application.HibernateConfiguration;
import de.tapp.entity.Termin;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@RestController
@CrossOrigin
@Transactional
public class TerminController {

    @GetMapping(path = "/termin")
    public Termin getTerminById(@RequestParam int terminId) {
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        Termin termin = session.load(Termin.class, terminId);
        session.close();
        return termin;
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
