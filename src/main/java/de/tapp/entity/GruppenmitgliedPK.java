package de.tapp.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GruppenmitgliedPK implements Serializable {
    @Column(name = "person_id", nullable = false)
    @Id
    private int personId;

    @Column(name = "person_id", nullable = false)
    @Id
    private int gruppenId;


    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }


    public int getGruppenId() {
        return gruppenId;
    }

    public void setGruppenId(int gruppenId) {
        this.gruppenId = gruppenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GruppenmitgliedPK that = (GruppenmitgliedPK) o;
        return personId == that.personId &&
                gruppenId == that.gruppenId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(personId, gruppenId);
    }
}
