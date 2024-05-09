package edu.esprit.entites;


import java.util.List;

public class Restaurant {
    private int id;
    private int numdetel;
    private int nmbetoiles;
    private String nom, adresse, image_restaurant, heure_ouverture, heure_fermeture;
    private List<Plat> platListe;


    public Restaurant(int id, String nom, String adresse, int numdetel, int nmbetoiles, String image_restaurant, String heure_ouvertire, String heure_fermeture) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.numdetel = numdetel;
        this.nmbetoiles = nmbetoiles;
        this.image_restaurant = image_restaurant;
        this.heure_ouverture = heure_ouvertire;
        this.heure_fermeture = heure_fermeture;


    }

    public Restaurant(String nom, String adresse, int numdetel, int nmbetoiles, String image_restaurant, String heure_ouvertire, String heure_fermeture) {
        this.nom = nom;
        this.adresse = adresse;
        this.numdetel = numdetel;
        this.nmbetoiles = nmbetoiles;
        this.image_restaurant = image_restaurant;
        this.heure_ouverture = heure_ouvertire;
        this.heure_fermeture = heure_fermeture;

    }

    public Restaurant() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumdetel() {
        return numdetel;
    }

    public void setNumdetel(int numdetel) {
        this.numdetel = numdetel;
    }

    public int getNmbetoiles() {
        return nmbetoiles;
    }

    public void setNmbetoiles(int nmbetoiles) {
        this.nmbetoiles = nmbetoiles;
    }

    public String getImage() {
        return image_restaurant;
    }

    public void setImage(String image_restaurant) {
        this.image_restaurant = image_restaurant;
    }

    public String getHeure_ouverture() {
        return heure_ouverture;
    }

    public void setHeure_ouverture(String heure_ouverture) {
        this.heure_ouverture = heure_ouverture;
    }

    public String getHeure_fermeture() {return heure_fermeture;}

    public void setHeure_fermeture(String heure_fermeture) {
        this.heure_fermeture = heure_fermeture;
    }
    public List<Plat> getPlatListe() {
        return platListe;
    }

    public void setPlatListe(List<Plat> platListe) {
        this.platListe = platListe;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Restaurant{id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", adresse='").append(adresse).append('\'');
        sb.append(", numdetel=").append(numdetel);
        sb.append(", nmbetoiles=").append(nmbetoiles);
        sb.append(", image_restaurant='").append(image_restaurant).append('\'');
        sb.append(", heure_ouverture='").append(heure_ouverture).append('\'');
        sb.append(", heure_fermeture='").append(heure_fermeture).append('\'');

        return sb.toString();
    }

}
