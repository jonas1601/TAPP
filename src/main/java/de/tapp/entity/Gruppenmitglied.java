package de.tapp.entity;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Gruppenmitglied {

    @EmbeddedId
    private GruppenmitgliedPK id;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name="gruppen_id")
    private Gruppe gruppe;

    @OneToOne
    @JoinColumn(name="rollen_id")
    private Rolle rolle;


    public GruppenmitgliedPK getId() {
        return id;
    }

    public void setId(GruppenmitgliedPK id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    public Gruppe getGruppe() {
        return gruppe;
    }
    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }


    public Rolle getRolle() {
        return rolle;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gruppenmitglied that = (Gruppenmitglied) o;
        return Objects.equals(person, that.person) &&
                Objects.equals(gruppe, that.gruppe) &&
                Objects.equals(rolle, that.rolle);
    }


}
