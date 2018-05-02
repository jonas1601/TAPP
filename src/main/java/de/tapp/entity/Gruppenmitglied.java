package de.tapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Gruppenmitglied implements Serializable {

    @Id
    @Column(name = "person_id", nullable = false)
    private int personId;

    @Id
    @Column(name = "gruppen_id", nullable = false)
    private int gruppenId;

    @OneToOne
    @JoinColumn(name = "rollen_id")
    private Rolle rolle;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }


    @Basic
    @Column(name = "gruppen_id", nullable = false, length = 2147483647)
    public int getGruppenId() {
        return gruppenId;
    }

    public void setGruppenId(int gruppenId) {
        this.gruppenId = gruppenId;
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
        return Objects.equals(personId, that.personId) &&
                Objects.equals(gruppenId, that.gruppenId) &&
                Objects.equals(rolle, that.rolle);
    }


}
