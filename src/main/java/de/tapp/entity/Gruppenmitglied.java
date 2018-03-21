package de.tapp.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(GruppenmitgliedPK.class)
public class Gruppenmitglied {
    private int personId;
    private int gruppenId;
    private Integer rollenId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Id
    @Column(name = "gruppen_id", nullable = false)
    public int getGruppenId() {
        return gruppenId;
    }

    public void setGruppenId(int gruppenId) {
        this.gruppenId = gruppenId;
    }

    @Basic
    @Column(name = "rollen_id", nullable = true)
    public Integer getRollenId() {
        return rollenId;
    }

    public void setRollenId(Integer rollenId) {
        this.rollenId = rollenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gruppenmitglied that = (Gruppenmitglied) o;
        return personId == that.personId &&
                gruppenId == that.gruppenId &&
                Objects.equals(rollenId, that.rollenId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(personId, gruppenId, rollenId);
    }
}
