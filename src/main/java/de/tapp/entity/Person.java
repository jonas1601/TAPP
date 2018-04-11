package de.tapp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Person implements Serializable{
    private int personId;
    private String benutzername;
    private String vorname;
    private String nachname;
    private String notificationToken;
    private String pass;
    private List<Gruppenmitglied> gruppenmitglieder;

    @OneToMany(mappedBy = "person")
    public List<Gruppenmitglied> getGruppenmitglieder(){
        return gruppenmitglieder;
    }

    public void setGruppenmitglieder(List<Gruppenmitglied> gruppenmitglieder){
        this.gruppenmitglieder = gruppenmitglieder;
    }

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
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
                Objects.equals(pass, person.pass);
    }

    @Override
    public int hashCode() {

        return Objects.hash(personId, benutzername, vorname, nachname, notificationToken, pass);
    }
}
