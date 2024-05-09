package edu.esprit.services;



import edu.esprit.entites.Plat;
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

public class PlatService implements IService<Plat> {

    private Connection connection;

    public PlatService() {
        connection = MyConnection.getInstance().getConnection();
    }


    @Override
    public void ajouter(Plat plat) throws SQLException {
        String sql = "INSERT INTO plat (nom, description, image_plat) " +
                "VALUES('" + plat.getNom() + "','" + plat.getDescription() + "','" + plat.getImage() + "')";


        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }



    @Override
    public void modifier(Plat plat) throws SQLException {
        String sql = "update plat set nom = ?, description = ?, image_plat = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, plat.getNom());
        preparedStatement.setString(2, plat.getDescription());
        preparedStatement.setString(3, plat.getImage());
        preparedStatement.setInt(4, plat.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from plat where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Plat> recuperer() throws SQLException {
        String sql = "select * from plat";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Plat> plat = new ArrayList<>();
        while (rs.next()) {
            Plat p = new Plat();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setDescription(rs.getString("description"));
            p.setImage(rs.getString("image_plat"));



            plat.add(p);
        }
        return plat;
    }

    @Override
    public List<Plat> getAll() throws SQLException {
        String sql = "select * from plat";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Plat> plats = FXCollections.observableArrayList();
        while (rs.next()) {
            Plat u = new Plat();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setDescription(rs.getString("description"));
            u.setImage(rs.getString("image_plat"));
            plats.add(u);
        }
        return plats;
    }

    public List<Plat> getPlatsByRestaurantId(int restaurantId) throws SQLException {
        String sql = "SELECT * FROM plat WHERE restaurant_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, restaurantId);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Plat> plats = new ArrayList<>();
        while (resultSet.next()) {
            Plat plat = new Plat();
            plat.setId(resultSet.getInt("id"));
            plat.setNom(resultSet.getString("nom"));
            plat.setDescription(resultSet.getString("description"));
            plat.setImage(resultSet.getString("image_plat"));
            plats.add(plat);
        }

        return plats;
    }
}
