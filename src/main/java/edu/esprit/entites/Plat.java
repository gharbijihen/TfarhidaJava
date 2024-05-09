package edu.esprit.entites;


public class Plat {
    private int id;
    private String nom, description, image_plat;
    private Restaurant restaurant;



    public Plat(int id, String nom, String description, String image_plat, Restaurant restaurant) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image_plat = image_plat;
        this.restaurant = restaurant;

    }

    public Plat(String nom, String description, String image_plat, int restaurantId) {
        this.nom = nom;
        this.description = description;
        this.image_plat = image_plat;


    }

    public Plat() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getImage() {
        return image_plat;
    }

    public void setImage(String image_plat) {
        this.image_plat = image_plat;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }



    @Override
    public String toString() {
        return "Plat{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image_plat='" + image_plat + '\'' +
                ", restaurant=" + (restaurant != null ? restaurant.getNom() : "Aucun restaurant associé") +
                '}';
    }

}
