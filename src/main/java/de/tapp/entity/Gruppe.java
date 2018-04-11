package de.tapp.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@javax.persistence.Entity
public class Gruppe implements Serializable {
    private int gruppenId;
    private String name;

    @Id
    @Column(name = "gruppen_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getGruppenId() {
        return gruppenId;
    }

    public void setGruppenId(int gruppenId) {
        this.gruppenId = gruppenId;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gruppe gruppe = (Gruppe) o;
        return gruppenId == gruppe.gruppenId &&
                Objects.equals(name, gruppe.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(gruppenId, name);
    }
}
