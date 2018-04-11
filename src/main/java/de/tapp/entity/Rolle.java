package de.tapp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Rolle implements Serializable {
    private int rollenId;
    private String beschreibung;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rollen_id", nullable = false)
    public int getRollenId() {
        return rollenId;
    }

    public void setRollenId(int rollenId) {
        this.rollenId = rollenId;
    }

    @Basic
    @Column(name = "beschreibung", nullable = true, length = 2147483647)
    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rolle rolle = (Rolle) o;
        return rollenId == rolle.rollenId &&
                Objects.equals(beschreibung, rolle.beschreibung);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rollenId, beschreibung);
    }
}
