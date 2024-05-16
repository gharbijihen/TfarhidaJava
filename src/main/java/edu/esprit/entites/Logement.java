package edu.esprit.entites;

public class Logement {
    public int id;
    public String nom;
    public String localisation;
    public int num;

    public int prix;
    public String image;
    public String etat;
    public String type_log;

    public int note_moyenne;

//public EquipementCrud equipementCrud;

    //public int equipement_id;
    public Equipement equipement_id;

    public int userid;
    private Equipement equipement;
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Logement(String nom, String localisation, int num, int prix, String image, String etat, String type_log, int note_moyenne) {
        this.nom = nom;
        this.localisation = localisation;
        this.num = num;
        this.prix = prix;
        this.image = image;
        this.etat = etat;
        this.type_log = type_log;
        this.note_moyenne = note_moyenne;
        //  this.equipement = equipement;
    }

    public Logement(int i) {

    }

    public Logement(int id, Equipement equipement_id, String nom, int prix, String localisation, int num, String etat, int note_moyenne, String type_log, String image) {
        this.id = id;
        this.equipement_id = equipement_id;
        this.nom = nom;

        this.localisation = localisation;
        this.num = num;
        this.prix = prix;
        this.image = image;
        this.etat = etat;
        this.type_log = type_log;
        this.note_moyenne = note_moyenne;
    }

    public Logement() {

    }


    public Equipement getEquipement_id() {
        return equipement_id;
    }

    public void setEquipement_id(Equipement equipement_id) {
        this.equipement_id = equipement_id;
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

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getType_log() {
        return type_log;
    }

    public void setType_log(String type_log) {
        this.type_log = type_log;
    }

    public int getNote_moyenne() {
        return note_moyenne;
    }

    public void setNote_moyenne(int note_moyenne) {
        this.note_moyenne = note_moyenne;
    }




    @Override
    public String toString() {
        return "Logement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", localisation='" + localisation + '\'' +
                ", num=" + num +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", etat='" + etat + '\'' +
                ", type_log='" + type_log + '\'' +
                ", note_moyenne=" + note_moyenne +
                ", equipement=" + equipement_id +
                '}';
    }
}
