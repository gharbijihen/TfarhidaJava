package Service;

import Entities.User;
import Utils.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entities.Role;
import javafx.scene.control.Alert;

import org.mindrot.jbcrypt.BCrypt;

public class ServiceUser implements IService<User> {
    private Connection connection = Datasource.getConn();

    public ServiceUser() {
    }


    @Override
    public void add(User user) throws SQLException {
        String query = "INSERT INTO user (username, first_name, last_name, numero, email, roles, password, is_verified, reset_token) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getFirst_name());
        statement.setString(3, user.getLast_name());
        statement.setInt(4, user.getNumero());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getRoles());
        statement.setString(7, user.getPassword());
        statement.setBoolean(8, user.getIs_verified());
        statement.setString(9, user.getReset_token());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE user SET username = ?, first_name = ?, last_name = ?, numero = ?, email = ?, roles = ?, password = ?, is_verified = ?, reset_token = ? "
                + "WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getFirst_name());
        statement.setString(3, user.getLast_name());
        statement.setInt(4, user.getNumero());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getRoles());
        statement.setString(7, user.getPassword());
        statement.setBoolean(8, user.getIs_verified());
        statement.setString(9, user.getReset_token());
        statement.setInt(10, user.getId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void delete(User user) throws SQLException {
        String query = "DELETE FROM user WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public User findById(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = mapResultSetToUser(resultSet);
        }
        resultSet.close();
        statement.close();
        return user;
    }

    @Override
    public List<User> ReadAll() throws SQLException {
        String query = "SELECT * FROM user";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            User user = mapResultSetToUser(resultSet);
            userList.add(user);
        }
        resultSet.close();
        statement.close();
        System.out.println(userList.toString());
        return userList;
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        int numero = resultSet.getInt("numero");
        String email = resultSet.getString("email");
        String roles = resultSet.getString("roles");
        String password = resultSet.getString("password");
        boolean is_verified = resultSet.getBoolean("is_verified");
        String reset_token = resultSet.getString("reset_token");
        return new User(id, username, first_name, last_name, numero, email, roles, password, is_verified, reset_token);
    }
    public User findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = mapResultSetToUser(resultSet);
        }
        resultSet.close();
        statement.close();
        return user;
    }
    public boolean emailExists(String email){
        String query = "SELECT  COUNT(*) FROM user WHERE email = ?";
        try{ PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; //if count>0 email exists
            }
        }catch(SQLException e){
            System.err.println("error checking email existence "+e.getMessage());
        }
        return false;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public User login(String email, String password) throws SQLException {
        User user = null;
        if (!emailExists(email)) {
            System.out.println("email doesn't exist");
            showAlert("email non existante");
            return null;
        }
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
          //   statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                  //  user.setName(resultSet.getString("name"));
                   // user.setLastName(resultSet.getString("lastname"));
                    user.setRoles(resultSet.getString("roles"));
                    if (BCrypt.checkpw(password,user.getPassword()))
                        return user; // User found with the given email, password, and role
                    else
                        return null;
            }
            return null; // No user found with the given email, password, and role
        }
    }

}