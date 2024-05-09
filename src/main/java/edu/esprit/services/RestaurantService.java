package edu.esprit.services;



import edu.esprit.tools.MyConnection;
import edu.esprit.entites.Restaurant;

import edu.esprit.tools.MyConnection;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import edu.esprit.entites.Restaurant;
import edu.esprit.services.RestaurantService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService implements IService<Restaurant> {

    private Connection connection;

    public RestaurantService() {
        connection = MyConnection.getInstance().getConnection();
    }

    @Override
    public void ajouter(Restaurant restaurant) throws SQLException {
        String sql = "INSERT INTO restaurant (nom, adresse, numdetel, nmbetoiles, image_restaurant, heure_ouverture, heure_fermeture) " +
                "VALUES('" + restaurant.getNom() + "','" + restaurant.getAdresse() + "'" +
                ",'" + restaurant.getNumdetel() + "','" + restaurant.getNmbetoiles() + "'" +
                ",'" + restaurant.getImage() + "','" + restaurant.getHeure_ouverture() + "','" + restaurant.getHeure_fermeture() + "')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }



    @Override
    public void modifier(Restaurant restaurant) throws SQLException {
        String sql = "update restaurant set nom = ?, adresse = ?, numdetel = ?, nmbetoiles = ?, image_restaurant = ?, heure_ouverture = ?, heure_fermeture = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, restaurant.getNom());
        preparedStatement.setString(2, restaurant.getAdresse());
        preparedStatement.setInt(3, restaurant.getNumdetel());
        preparedStatement.setInt(4, restaurant.getNmbetoiles());
        preparedStatement.setString(6, restaurant.getImage());
        preparedStatement.setString(7, restaurant.getHeure_ouverture());
        preparedStatement.setString(8, restaurant.getHeure_fermeture());
        preparedStatement.setInt(9, restaurant.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from restaurant where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Restaurant> recuperer() throws SQLException {
        String sql = "select * from restaurant";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Restaurant> restaurant = new ArrayList<>();
        while (rs.next()) {
            Restaurant p = new Restaurant();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setAdresse(rs.getString("adresse"));
            p.setNumdetel(rs.getInt("numdetel"));
            p.setNmbetoiles(rs.getInt("nmbetoiles"));
            p.setImage(rs.getString("image_restaurant"));
            p.setHeure_ouverture(rs.getString("heure_ouverture"));
            p.setHeure_fermeture(rs.getString("heure_fermeture"));


            restaurant.add(p);
        }
        return restaurant;
    }

    @Override
    public List<Restaurant> getAll() throws SQLException {
        String sql = "select * from restaurant";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Restaurant> restaurants = FXCollections.observableArrayList();
        while (rs.next()) {
            Restaurant u = new Restaurant();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setAdresse(rs.getString("adresse"));
            u.setNumdetel(rs.getInt("numdetel"));
            //u.setImage(rs.getString("image"));
            u.setNmbetoiles(rs.getInt("nmbetoiles"));
            u.setImage(rs.getString("image_restaurant"));
            u.setHeure_ouverture(rs.getString("heure_ouverture"));
            u.setHeure_fermeture(rs.getString("heure_fermeture"));
            restaurants.add(u);
        }
        return restaurants;
    }
}
