package de.tapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;
import sun.util.resources.cldr.lag.LocaleNames_lag;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Proxy(lazy = false)
public class Termin {
    private int terminId;
    private String titel;
    private String beschreibung;
    private LocalDateTime anfang;
    private LocalDateTime ende;
    private Integer ganztaegig;
    private int gruppenId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "termin_id", nullable = false)
    public int getTerminId() {
        return terminId;
    }

    public void setTerminId(int terminId) {
        this.terminId = terminId;
    }

    @Basic
    @Column(name = "titel", nullable = true, length = 2147483647)
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    @Basic
    @Column(name = "beschreibung", nullable = true, length = 2147483647)
    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Basic
    @Column(name = "anfang", nullable = true)
    public LocalDateTime getAnfang() {
        return anfang;
    }

    public void setAnfang(LocalDateTime anfang) {
        this.anfang = anfang;
    }

    @Basic
    @Column(name = "ende", nullable = true)
    public LocalDateTime getEnde() {
        return ende;
    }

    public void setEnde(LocalDateTime ende) {
        this.ende = ende;
    }

    @Basic
    @Column(name = "ganztaegig", nullable = true)
    public Integer getGanztaegig() {
        return ganztaegig;
    }

    public void setGanztaegig(Integer ganztaegig) {
        this.ganztaegig = ganztaegig;
    }

    @Basic
    @Column(name = "gruppen_id", nullable = false)
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
        Termin termin = (Termin) o;
        return terminId == termin.terminId &&
                gruppenId == termin.gruppenId &&
                Objects.equals(titel, termin.titel) &&
                Objects.equals(beschreibung, termin.beschreibung) &&
                Objects.equals(anfang, termin.anfang) &&
                Objects.equals(ende, termin.ende) &&
                Objects.equals(ganztaegig, termin.ganztaegig);
    }

    @Override
    public int hashCode() {

        return Objects.hash(terminId, titel, beschreibung, anfang, ende, ganztaegig, gruppenId);
    }
}
