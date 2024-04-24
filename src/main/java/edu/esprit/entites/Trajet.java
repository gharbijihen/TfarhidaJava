package edu.esprit.entites;

import java.sql.Date;

public class Trajet {
    public int id;
    public String lieu_depart;
    public String lieu_arrivee;
    public String heure;

    public Date date;
    public int moyen_transport_id;
    private static Moyen_transport moyen_transport;



    public Trajet(String lieu_depart, String lieu_arrivee, String heure, Date date,int moyen_transport_id) {
        this.lieu_depart = lieu_depart;
        this.lieu_arrivee = lieu_arrivee;
        this.heure = heure;
        this.date = date;
        this.moyen_transport_id = moyen_transport_id;
    }

    public Trajet() {

    }
    public static Moyen_transport getMoyen_transport() {
        return moyen_transport;
    }

    public void setMoyen_transport(Moyen_transport moyen_transport) {
        Trajet.moyen_transport = moyen_transport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLieu_depart() {
        return lieu_depart;
    }

    public void setLieu_depart(String lieu_depart) {
        this.lieu_depart = lieu_depart;
    }

    public String getLieu_arrivee() {
        return lieu_arrivee;
    }

    public void setLieu_arrivee(String lieu_arrivee) {
        this.lieu_arrivee = lieu_arrivee;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMoyen_transport_id() {
        return moyen_transport_id;
    }

    public void setMoyen_transport_id(int moyen_transport_id) {
        this.moyen_transport_id = moyen_transport_id;
    }



    @Override
    public String toString() {
        return "Logement{" +
                "id=" + id +
                ", lieu_depart='" + lieu_depart + '\'' +
                ", lieu_arrivee='" + lieu_arrivee + '\'' +
                ", heure=" + heure +
                ", date=" + date +
                ", moyen_transport_id='" + moyen_transport_id +
                '}';
    }
}
