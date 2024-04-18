package edu.esprit.entites;


import javafx.scene.control.TextField;

public class Activite {
    public int id;
    public String nom;
    public int prix;
    public String localisation;
    public int nb_P;
    public String etat;
    public String image;
    public String description_act;
    public int categorie_id;







    public Activite(int id, int categorie_id, String nom, int prix, String localisation, int nb_P, String etat, String description_act, String image) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.localisation = localisation;
        this.nb_P = nb_P;
        this.etat = etat;
        this.description_act = description_act;
        this.categorie_id = categorie_id;
        this.image=image;
    }
    public Activite(int categorie_id, String nom, int prix, String localisation, int nb_P, String etat, String description_act, String image) {
        this.nom = nom;
        this.prix = prix;
        this.localisation = localisation;
        this.nb_P = nb_P;
        this.etat = etat;
        this.description_act = description_act;
        this.categorie_id = categorie_id;
        this.image=image;
    }


    public Activite(int categorie_id, String nom, int prix, String localisation, int nb_P, String etat, String description_act) {
        this.nom = nom;
        this.prix = prix;
        this.localisation = localisation;
        this.nb_P = nb_P;
        this.etat = etat;
        this.description_act = description_act;
        this.categorie_id = categorie_id;
    }
    public Activite(){}

    public Activite(String nom, int prix, String localisation, int nbP, String etat, String descriptionAct, int categorieId) {
        this.nom = nom;
        this.prix = prix;
        this.localisation = localisation;
        this.nb_P = nb_P;
        this.etat = etat;
        this.description_act = description_act;
        this.categorie_id = categorie_id;
        this.image=image;
    }
    /*public Activite(String nom, int prix, String localisation, int nbP, String etat, String descriptionAct, int categorieId) {
        this.nom = nom;
        this.prix = prix;
        this.localisation = localisation;
        this.nb_P = nb_P;
        this.etat = etat;
        this.description_act = description_act;
        this.categorie_id = categorie_id;
        this.image=image;
    }*/


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

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getNb_P() {
        return nb_P;
    }

    public void setNb_P(int nb_P) {
        this.nb_P = nb_P;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription_act() {
        return description_act;
    }

    public void setDescription_act(String description_act) {
        this.description_act = description_act;
    }

    public int getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", localisation='" + localisation + '\'' +
                ", nb_P=" + nb_P +
                ", etat='" + etat + '\'' +
                ", image='" + image + '\'' +
                ", description_act='" + description_act + '\'' +
                ", categorie_id=" + categorie_id +
                '}';
    }
}

