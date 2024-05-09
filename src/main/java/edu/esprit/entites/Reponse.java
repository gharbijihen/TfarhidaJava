package edu.esprit.entites;

import java.sql.Date;

public class Reponse {

    public int id;
    public String description;
    public Date date ;

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    public Reponse() {

    }


    public Reponse(int id, String description, Date date) {
        this.id = id;
        this.description = description;
        this.date = date;
    }


    public Reponse(String description, Date date) {
        this.description = description;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
