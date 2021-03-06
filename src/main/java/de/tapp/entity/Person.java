package de.tapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
@Proxy(lazy = false)
public class Person  implements Serializable{
    private int personId;
    private String benutzername;
    private String vorname;
    private String nachname;
    private String notificationToken;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "benutzername", nullable = true, length = 2147483647)
    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    @Basic
    @Column(name = "vorname", nullable = true, length = 2147483647)
    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    @Basic
    @Column(name = "nachname", nullable = true, length = 2147483647)
    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    @Basic
    @Column(name = "notification_token", nullable = true, length = 2147483647)
    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    @Basic
    @Column(name = "pass", nullable = true, length = 2147483647)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId &&
                Objects.equals(benutzername, person.benutzername) &&
                Objects.equals(vorname, person.vorname) &&
                Objects.equals(nachname, person.nachname) &&
                Objects.equals(notificationToken, person.notificationToken) &&
                Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(personId, benutzername, vorname, nachname, notificationToken, password);
    }
}
