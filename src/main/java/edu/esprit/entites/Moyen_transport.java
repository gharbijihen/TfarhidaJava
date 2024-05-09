package edu.esprit.entites;
import java.util.List;


public class Moyen_transport {
    public int id;
    public String type;
    public int capacite;
    public String lieu;
    public String image;
    public boolean etat;
    public boolean valide;

    private List<Trajet> trajets;






     public Moyen_transport(String type, int capacite, String lieu, boolean etat, boolean valide, String image) {
        this.type = type;
        this.capacite = capacite;
        this.lieu = lieu;
        this.etat = etat;
        this.valide = valide;
        this.image = image;
    }
    public Moyen_transport(){};


    public List<Trajet> getTrajets() {
        return trajets;
    }

    public void setTrajets(List<Trajet> trajets) {
        this.trajets = trajets;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




    @Override
    public String toString() {
        return type;
    }
}
