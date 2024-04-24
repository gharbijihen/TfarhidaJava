package edu.esprit.entites;

import java.sql.Date;

public class Reclamation {

    public int id;
    public String titre;
    public String description_reclamation;
    public  Boolean etat;
    public Date date;

    public String image;

    public String type;
    public int reponseid;

    public Reponse getReponse() {
        return reponse;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description_reclamation='" + description_reclamation + '\'' +
                ", etat=" + etat +
                ", date=" + date +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                ", reponseid=" + reponseid +
                ", reponse=" + reponse +
                '}';
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    public Reponse reponse;

    public int getReponseid() {
        return reponseid;
    }

    public void setReponseid(int reponseid) {
        this.reponseid = reponseid;
    }

    public Reclamation() {
        this.etat = false;

    }

    public Reclamation(int id, String titre, String description_reclamation, Boolean etat, Date date, String image, String type) {
        this.id = id;
        this.titre = titre;
        this.description_reclamation = description_reclamation;
        this.etat = false;
        this.date = date;
        this.image = image;
        this.type = type;
    }

    public Reclamation(String titre, String description_reclamation, Boolean etat, Date date, String image, String type) {
        this.titre = titre;
        this.description_reclamation = description_reclamation;
        this.etat = false;
        this.date = date;
        this.image = image;
        this.type = type;
    }

//    public Reclamation(String titre, String type, String description, Date date, boolean etat) {
//        this.etat = false;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription_reclamation() {
        return description_reclamation;
    }

    public void setDescription_reclamation(String description_reclamation) {
        this.description_reclamation = description_reclamation;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
