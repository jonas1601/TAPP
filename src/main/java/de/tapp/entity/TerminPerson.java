package de.tapp.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "termin_person", schema = "dbo", catalog = "TAPP")
@IdClass(TerminPersonPK.class)
public class TerminPerson {
    private int terminId;
    private int personId;
    private int statusId;
    private String kommentar;
    private String datumAenderung;

    @Id
    @Column(name = "termin_id", nullable = false)
    public int getTerminId() {
        return terminId;
    }

    public void setTerminId(int terminId) {
        this.terminId = terminId;
    }

    @Id
    @Column(name = "person_id", nullable = false)
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "status_id", nullable = false)
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "kommentar", nullable = true, length = 2147483647)
    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    @Basic
    @Column(name = "datum_aenderung", nullable = true, length = 2147483647)
    public String getDatumAenderung() {
        return datumAenderung;
    }

    public void setDatumAenderung(String datumAenderung) {
        this.datumAenderung = datumAenderung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerminPerson that = (TerminPerson) o;
        return terminId == that.terminId &&
                personId == that.personId &&
                statusId == that.statusId &&
                Objects.equals(kommentar, that.kommentar) &&
                Objects.equals(datumAenderung, that.datumAenderung);
    }

    @Override
    public int hashCode() {

        return Objects.hash(terminId, personId, statusId, kommentar, datumAenderung);
    }
}
