package edu.esprit.entites;

public class Equipement {
    public int id;
    public boolean parking;
    public boolean inetrnet;
    public boolean climatisation;

    public int nbr_chambre;
    public String description;
    public String type_chambre;



    public Equipement(boolean parking, boolean inetrnet, boolean climatisation, int nbr_chambre, String description, String type_chambre) {
        this.parking = parking;
        this.inetrnet = inetrnet;
        this.climatisation = climatisation;
        this.nbr_chambre = nbr_chambre;
        this.description = description;
        this.type_chambre = type_chambre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isInetrnet() {
        return inetrnet;
    }

    public void setInetrnet(boolean inetrnet) {
        this.inetrnet = inetrnet;
    }

    public boolean isClimatisation() {
        return climatisation;
    }

    public void setClimatisation(boolean climatisation) {
        this.climatisation = climatisation;
    }

    public int getNbr_chambre() {
        return nbr_chambre;
    }

    public void setNbr_chambre(int nbr_chambre) {
        this.nbr_chambre = nbr_chambre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType_chambre() {
        return type_chambre;
    }

    public void setType_chambre(String type_chambre) {
        this.type_chambre = type_chambre;
    }
    @Override
    public String toString() {
        return "Equipement{" +
                "id=" + id +
                ", parking=" + parking +
                ", inetrnet=" + inetrnet +
                ", climatisation=" + climatisation +
                ", nbr_chambre=" + nbr_chambre +
                ", description='" + description + '\'' +
                ", type_chambre='" + type_chambre + '\'' +
                '}';
    }
}
