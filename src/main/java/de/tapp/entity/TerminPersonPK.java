package de.tapp.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TerminPersonPK implements Serializable {
    private int terminId;
    private int personId;

    @Column(name = "termin_id", nullable = false)
    @Id
    public int getTerminId() {
        return terminId;
    }

    public void setTerminId(int terminId) {
        this.terminId = terminId;
    }

    @Column(name = "person_id", nullable = false)
    @Id
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerminPersonPK that = (TerminPersonPK) o;
        return terminId == that.terminId &&
                personId == that.personId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(terminId, personId);
    }
}
