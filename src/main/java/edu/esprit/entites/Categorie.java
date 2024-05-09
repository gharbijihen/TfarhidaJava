package edu.esprit.entites;

public class Categorie {
    public int id;
    public String description;
    public String type_categorie;

    public Categorie(int id, String description, String type_categorie) {
        this.id = id;
        this.description = description;
        this.type_categorie = type_categorie;
    }
    public Categorie( String description, String type_categorie) {

        this.description = description;
        this.type_categorie = type_categorie;
    }

    public Categorie() {
        this.id = id;
        this.description = description;
        this.type_categorie = type_categorie;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType_categorie() {
        return type_categorie;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", type_categorie='" + type_categorie + '\'' +
                '}';
    }

    public void setType_categorie(String type_categorie) {
        this.type_categorie = type_categorie;
    }
}

