package de.tapp.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Terminstatus {
    private int statusId;
    private String beschreibung;

    @Id
    @Column(name = "status_id", nullable = false)
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
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
        Terminstatus that = (Terminstatus) o;
        return statusId == that.statusId &&
                Objects.equals(beschreibung, that.beschreibung);
    }

    @Override
    public int hashCode() {

        return Objects.hash(statusId, beschreibung);
    }
}
